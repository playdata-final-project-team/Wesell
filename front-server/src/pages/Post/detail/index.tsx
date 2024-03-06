import { useNavigate, useParams } from 'react-router-dom';
import './style.css';
import axios from 'axios';
import { useEffect, useState } from 'react';
import TextEditor from 'components/Quill/Detail';
import CommentList from 'components/CommentList';
import { getNicknameRequest } from 'apis';

interface postDetail {
  boardTitle: string;
  title: string;
  content: string;
  click: number;
  writer: string;
  createdAt: string;
  comments: {
    writer: string;
    content: string;
    createdAt: string;
  }[];
}

const PostDetail = () => {
  const navigator = useNavigate();
  const { boardId, postId } = useParams();
  const uuid = sessionStorage.getItem('uuid');

  // state: 게시판 제목 //
  const [boardTitle, setBoardTitle] = useState<string>('');

  // state: 게시글 제목 //
  const [title, setTitle] = useState<string>('');

  // state: 게시글 내용 //
  const [content, setContent] = useState<string>('');

  // state: 작성자 //
  const [writer, setWriter] = useState<string>('');

  // state: 생성일자 //
  const [createdAt, setCreatedAt] = useState<string>('');

  // state: 게시글 조회수 //
  const [click, setClick] = useState<number>(0);

  // state: 작성자 여부 확인 //
  const [isWriter, setIsWriter] = useState<boolean>(false);

  // comment: 수정 + 삭제 관련 - 해당 회원만 삭제 수정이 가능하도록 로직 수정 예정 //
  // event-handler: 게시글 삭제 버튼 핸들러 //
  const onPostDeleteBtnHandler = async () => {
    if (confirm('정말로 게시글을 삭제하시겠습니까?')) {
      try {
        const response = await axios.delete(`/board-service/api/v1/post/delete/${postId}`);
        const message = response.data;
        alert(`😀 ${message}`);
        navigator(`/board/${boardId}`);
      } catch (error) {
        console.log(error);
        alert('😒 게시글 삭제 실패');
        return;
      }
    } else {
      return;
    }
  };

  // function: 작성자 여부 확인 함수 //
  const confirmWriter = async (postWriter: string) => {
    if (!uuid) {
      return;
    }

    const nickname = await getNicknameRequest(uuid);

    if (postWriter === nickname) {
      setIsWriter(true);
    } else {
      setIsWriter(false);
    }
  };

  // function: 게시글 상세 정보 조회 함수 //
  const fetchPost = async () => {
    try {
      const response = await axios.get(`/board-service/api/v1/post/${postId}`);
      const responseData = response.data as postDetail;
      const { boardTitle, title, content, click, createdAt, writer } = responseData;
      setBoardTitle(boardTitle);
      setTitle(title);
      setContent(content);
      setClick(click);
      setCreatedAt(createdAt);
      setWriter(writer);
      await confirmWriter(writer);
    } catch (error) {
      console.error('⚠️ 게시글 조회 시 오류 발생', error);
    }
  };

  useEffect(() => {
    fetchPost();
  }, []);

  return (
    <div className="post-detail-wrapper">
      <div className="post-detail-container">
        <h2 className="board-title">{boardTitle}</h2>
        <div className="post-detail-top">
          <div className="post-title">{title}</div>
          <div className="post-detail-box">
            <div className="post-detail-box-right">
              <p>{writer}</p>
              <p>|</p>
              <p>{createdAt}</p>
            </div>
            <div className="post-detail-box-left">
              <p>조회 {click}</p>
            </div>
          </div>
        </div>
        <TextEditor htmlContent={content} />
        <div className="post-detail-btns">
          <button
            className="post-list-btn"
            onClick={() => {
              navigator(`/board/${boardId}`);
            }}
          >
            목록
          </button>
          {isWriter && (
            <>
              <button
                className="post-update-btn"
                onClick={() => {
                  navigator(`/post/${boardId}/${postId}/update`);
                }}
              >
                수정
              </button>
              <button className="post-delete-btn" onClick={onPostDeleteBtnHandler}>
                삭제
              </button>
            </>
          )}
        </div>
        <CommentList></CommentList>
      </div>
    </div>
  );
};

export default PostDetail;
