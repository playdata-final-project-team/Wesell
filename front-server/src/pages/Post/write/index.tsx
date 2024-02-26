import TextEditor from 'components/Quill/Write';
import './style.css';
import { useRef, useState } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';

const PostWrite = () => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const quillRef: any = useRef();

  const { boardId } = useParams();

  const navigator = useNavigate();

  const uuid = sessionStorage.getItem('uuid'); // 로그인 회원 uuid

  const [htmlContent, setHtmlContent] = useState<string>('');
  const [title, setTitle] = useState<string>('');

  // event-handler: 등록 버튼 핸들러 //
  const RegisterBtnHandler = async () => {
    const request = {
      title: title,
      content: htmlContent,
      uuid: uuid,
    };
    //axios 요청
    try {
      const response = await axios.post(`/board-service/api/v1/post/${boardId}`, request);
      const message = response.data;
      alert(`😀 ${message}`);
      navigator(`/board/${boardId}`);
    } catch (error) {
      alert('😒 게시글 등록 실패');
    }
  };

  return (
    <div className="post-write-wrapper">
      <div className="post-write-container">
        <h2 className="board-title">게시판 명</h2>

        <div className="post-write-title">
          <input
            placeholder="제목을 입력해주세요."
            value={title}
            onChange={(event) => setTitle(event.target.value)}
          />
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
              navigator(`/board/${boardId}`);
            }}
          >
            취소
          </button>
          <button className="post-write-ok-btn" onClick={RegisterBtnHandler}>
            등록
          </button>
        </div>
      </div>
    </div>
  );
};

export default PostWrite;
