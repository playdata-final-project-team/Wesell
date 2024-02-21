import CommentItem from 'components/CommentItem';
import './style.css';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import { Comment } from 'types/interface';

const CommentList = () => {
  const { boardId, postId } = useParams();

  const uuid = sessionStorage.getItem('uuid');

  // state: 현재 페이지 //
  const [currentPage, setCurrentPage] = useState<number>(0);

  // state: 전체 페이지 //
  const [totalPages, setTotalPages] = useState<number>(0);

  // state : 페이지 그룹 //
  const [pageGroup, setPageGroup] = useState<number>(0);

  // state: 댓글 목록 //
  const [comments, setComments] = useState<Comment[]>([]);

  // state: 댓글 작성 - 내용 //
  const [content, setContent] = useState<string>('');

  const [totalElements, setTotalElements] = useState<number>(0);

  // function: 페이지 목록 생성 //
  const createPageArr = () => {
    const arr: number[] = new Array(totalPages);
    const first = (pageGroup - 1) * 5 + 1;
    let last = pageGroup * 5;

    if (last > totalPages) last = totalPages;

    for (let i = first; i <= last; i++) {
      arr[i] = i;
    }
    return arr;
  };

  // event-handler: 댓글 등록 이벤트 핸들러 //
  const onAddCommentEventHandler = async () => {
    try {
      if (content.trim() == '') return;
      const request = { postId: postId, content: content, writer: '작성자' };
      await axios.post('/board-service/api/v1/comments', request);
      setContent('');
      return;
    } catch (error) {
      console.error('⚠️ 댓글 등록 중 오류 발생', error);
    }
  };

  // comment : 페이지 목록 배열//
  const pageArr = createPageArr();

  // function: 이전 페이지 이동 함수 //
  const prevPage = () => {
    if (currentPage <= 0) return;

    setCurrentPage((n: number) => n - 1);
  };

  // function: 다음 페이지 이동 함수 //
  const nextPage = () => {
    if (currentPage >= totalPages - 1) return;

    setCurrentPage((n: number) => n + 1);
  };

  // function: 처음 페이지 이동 함수 //
  const firstPage = () => {
    setCurrentPage(0);
  };

  // function: 마지막 페이지 이동 함수 //
  const lastPage = () => {
    if (totalPages == 0) return;
    setCurrentPage(totalPages - 1);
  };

  // function: 댓글 목록 조회 함수 //
  const fetchComments = async () => {
    try {
      const response = await axios.get(`/board-service/api/v1/comments/${postId}`, {
        params: { page: currentPage, size: 10 },
      });

      console.log(response);
      const { dtoList, page, totalPages, totalElements } = response.data;
      setCurrentPage(page);
      setTotalPages(totalPages);
      setTotalElements(totalElements);
      setPageGroup(Math.ceil((page + 1) / 10));
      console.log(dtoList);
      setComments(dtoList);
    } catch (error) {
      console.error('⚠️ 채팅 목록 조회 오류 발생', error);
    }
  };

  useEffect(() => {
    fetchComments();
  }, [currentPage]);

  return (
    <div className="comment-list-wrapper">
      <div className="comment-list-count">
        전체 댓글 <span style={{ fontWeight: '700', marginLeft: '5px' }}>{totalElements}</span>개
      </div>
      <div className="comment-list-container">
        <div className="comment-write-box">
          <div className="comment-write-content">
            <textarea
              placeholder="욕설이나 폭언은 자제 부탁드립니다."
              value={content}
              onChange={(e) => setContent(e.target.value)}
            ></textarea>
          </div>
          <div className="comment-write-btn">
            <button onClick={onAddCommentEventHandler}>등록</button>
          </div>
        </div>
        <ul className="comment-list">
          {comments.map((comment, index) => (
            <div key={index}>
              <li>
                <CommentItem parentComment={comment} />
              </li>
              {comment.children &&
                comment.children.map((child, index) => (
                  <li key={index}>
                    <CommentItem childComment={child} />
                  </li>
                ))}
            </div>
          ))}
        </ul>
        <div className="comment-list-pagination">
          {totalPages !== 0 && (
            <>
              <button onClick={firstPage}>&lt;&lt;</button>
              <button onClick={prevPage}>&lt;</button>

              {pageArr.map((n: number) => (
                <button
                  key={n - 1}
                  style={currentPage === n - 1 ? { textDecoration: 'underline' } : {}}
                  onClick={() => {
                    setCurrentPage(n - 1);
                  }}
                >
                  {n}
                </button>
              ))}

              <button onClick={nextPage}>&gt;</button>
              <button onClick={lastPage}>&gt;&gt;</button>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default CommentList;
