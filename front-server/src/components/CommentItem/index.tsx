import axios from 'axios';
import './style.css';
import { Comment } from 'types/interface';
import { useEffect, useState } from 'react';
import { FaPlusSquare } from 'react-icons/fa';
import { FaSquareMinus } from 'react-icons/fa6';
import { getNicknameRequest } from 'apis';

interface Props {
  parentComment?: Comment;
  childComment?: Comment;
  postId?: number;
  writer?: string;
  fetchComments: () => void;
}

const CommentItem = (props: Props) => {
  const { childComment, parentComment, postId, writer } = props;

  const { fetchComments } = props;

  const [content, setContent] = useState<string>('');

  const [isVisible, setIsVisible] = useState<boolean>(false);

  const [nickname, setNickname] = useState<string>('');

  // event-handler: 게시글 삭제 버튼 핸들러 //
  const onPostDeleteBtnHandler = async () => {
    if (parentComment) {
      if (nickname !== parentComment.writer) {
        alert('🥹 본인이 작성한 댓글이 아닙니다.');
        return;
      }
    }
    if (childComment) {
      if (nickname !== childComment.writer) {
        alert('🥹 본인이 작성한 댓글이 아닙니다.');
        return;
      }
    }

    if (confirm('정말로 게시글을 삭제하시겠습니까?')) {
      try {
        const response = await axios.delete(
          `/board-service/api/v1/comments/${parentComment?.id || childComment?.id}`,
        );
        const message = response.data;
        alert(`😀 ${message}`);
        fetchComments();
        return;
      } catch (error) {
        console.log(error);
        alert('😒 댓글 삭제 실패');
        return;
      }
    } else {
      return;
    }
  };

  // event-handler: 대댓글 등록 버튼 핸들러 //
  const onAddCommentEventHandler = async () => {
    try {
      const request = {
        postId: postId,
        parentId: parentComment?.id,
        content: content,
        writer: writer,
      };
      await axios.post('/board-service/api/v1/comments', request);
      setContent('');
      fetchComments();
      return;
    } catch (error) {
      console.log(error);
      alert('😒 댓글 등록 실패');
      return;
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      console.log;

      if (!writer) {
        return;
      }

      const nickname = await getNicknameRequest(writer);
      setNickname(nickname);
    };

    fetchData();
  }, []);

  return (
    <div className="comment-item-box">
      {parentComment && (
        <>
          <div className="parent-comment">
            <div className="comment-writer">
              <span>{parentComment.writer}</span>
            </div>
            <div className="comment-content">
              <p>{parentComment.content}</p>
            </div>
            <div className="comment-write-date">
              <span>{parentComment.createdAt}</span>
            </div>
            <button className="delete-btn" onClick={onPostDeleteBtnHandler}>
              X
            </button>
          </div>
          <div className="comment-comment">
            <div className="reply-box-toggle" onClick={() => setIsVisible((prev) => !prev)}>
              {isVisible ? (
                <>
                  <FaSquareMinus style={{ fontSize: '12px' }} />
                  <div>숨기기</div>
                </>
              ) : (
                <>
                  <FaPlusSquare style={{ fontSize: '12px' }} />
                  <div>답글 달기</div>
                </>
              )}
            </div>
            <div className={`reply-box ${isVisible ? 'visible' : ''}`}>
              <textarea
                placeholder="욕설이나 폭언은 자제 부탁드립니다."
                value={content}
                onChange={(e) => setContent(e.target.value)}
              ></textarea>
              <button onClick={onAddCommentEventHandler}>등록</button>
            </div>
          </div>
        </>
      )}
      {childComment && (
        <div className="child-comment">
          <div className="comment-writer">
            <span>{childComment.writer}</span>
          </div>
          <div className="comment-content">
            <span>ㄴ</span>
            <p>{childComment.content}</p>
          </div>
          <div className="comment-write-date">
            <span>{childComment.createdAt}</span>
          </div>
          <button className="delete-btn" onClick={onPostDeleteBtnHandler}>
            X
          </button>
        </div>
      )}
    </div>
  );
};

export default CommentItem;
