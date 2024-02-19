import TextEditor from 'components/Quill/Write';
import './style.css';
import { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

interface postDetail {
  boardTitle: string;
  title: string;
  content: string;
}

const PostUpdate = () => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const quillRef: any = useRef();

  const { postId, boardId } = useParams();

  const navigator = useNavigate();

  const uuid = sessionStorage.getItem('uuid'); // 로그인 회원 uuid

  // state: 게시판 제목 //
  const [boardTitle, setBoardTitle] = useState<string>('');

  // state: 게시글 내용 //
  const [htmlContent, setHtmlContent] = useState<string>('');

  // state: 게시글 제목 //
  const [title, setTitle] = useState<string>('');

  // event-handler: 수정 버튼 핸들러 //
  const RegisterBtnHandler = async () => {
    const request = {
      title: title,
      content: htmlContent,
    };
    //axios 요청
    try {
      const response = await axios.put(`/board-service/api/v1/post/update/${postId}`, request);
      const message = response.data;
      alert(`😀 ${message}`);
      navigator(`/post/${boardId}/${postId}`);
    } catch (error) {
      alert('😒 게시글 수정 실패');
    }
  };

  // function: 게시글 상세 정보 조회 함수 //
  const fetchPost = async () => {
    try {
      const response = await axios.get(`/board-service/api/v1/post/${postId}`);
      const responseData = response.data as postDetail;
      const { boardTitle, title, content } = responseData;
      setBoardTitle(boardTitle);
      setTitle(title);
      setHtmlContent(content);
    } catch (error) {
      console.error('⚠️ 게시글 조회 시 오류 발생', error);
    }
  };

  useEffect(() => {
    fetchPost();
  }, []);

  return (
    <div className="post-write-wrapper">
      <div className="post-write-container">
        <h2 className="board-title">{boardTitle}</h2>

        <div className="post-write-title">
          <input value={title} onChange={(event) => setTitle(event.target.value)} />
        </div>

        <div className="post-write-content">
          <TextEditor
            quillRef={quillRef}
            htmlContent={htmlContent}
            setHtmlContent={setHtmlContent}
          ></TextEditor>
        </div>

        <div className="post-write-btns">
          <button
            className="post-write-cancel-btn"
            onClick={() => {
              navigator(`/post/${boardId}/${postId}`);
            }}
          >
            취소
          </button>
          <button className="post-write-ok-btn" onClick={RegisterBtnHandler}>
            수정
          </button>
        </div>
      </div>
    </div>
  );
};

export default PostUpdate;
