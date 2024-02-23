import { PostListItem } from 'types/interface';
import './style.css';
import { useEffect, useState } from 'react';
import PostList from 'components/PostList';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
const MainBoard = () => {
  const { boardId } = useParams();

  const navigator = useNavigate();

  // state: 게시글 목록 - 페이징 목록 갯수만큼 노출//
  const [posts, setPosts] = useState<PostListItem[]>([]);

  // state: 현재 페이지 //
  const [currentPage, setCurrentPage] = useState<number>(0);

  // state: 전체 페이지 //
  const [totalPages, setTotalPages] = useState<number>(0);

  // state : 페이지 그룹 //
  const [pageGroup, setPageGroup] = useState<number>(0);

  // state: 게시판 이름 //
  const [title, setTitle] = useState<string>('');

  // function: 페이지 목록 생성 //
  const createPageArr = () => {
    const arr: number[] = new Array(totalPages);
    console.log(pageGroup);
    const first = (pageGroup - 1) * 5 + 1;
    let last = pageGroup * 5;

    if (last > totalPages) last = totalPages;

    for (let i = first; i <= last; i++) {
      arr[i] = i;
    }
    return arr;
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

  // function: 게시글 목록 조회 함수//
  const fetchPosts = async () => {
    // axios 호출 추가 예정
    try {
      const response = await axios.get(`/board-service/api/v1/board/${boardId}`, {
        params: { page: currentPage, size: 10 },
      });

      const { dtoList, page, boardTitle, totalPages } = response.data;
      setCurrentPage(page);
      setPosts(dtoList);
      setTitle(boardTitle);
      setTotalPages(totalPages);
      setPageGroup(Math.ceil((page + 1) / 5));
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      console.error('⚠️ 게시판 내 게시글 조회 시 오류 발생', error);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, [boardId, currentPage]);

  return (
    <div className="main-board-wrapper">
      <div className="main-board-container">
        <h2 className="board-title">{title}</h2>
        <PostList posts={posts} boardId={boardId} />
        <div className="main-board-under-bar">
          <div className="main-board-pagination">
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
        <button
          className="main-board-btn"
          type="button"
          onClick={() => {
            navigator(`/post/${boardId}/write`);
          }}
        >
          글등록
        </button>
      </div>
    </div>
  );
};

export default MainBoard;
