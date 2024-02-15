import { useNavigate, useParams } from 'react-router-dom';
import './style.css';
import axios from 'axios';
import { useEffect, useRef, useState } from 'react';
import TextEditor from 'components/Quill/Detail';

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
              <p>|</p>
              <p>댓글</p>
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
          <button className="post-update-btn">수정</button>
          <button className="post-delete-btn">삭제</button>
        </div>
      </div>
    </div>
  );
};

export default PostDetail;
