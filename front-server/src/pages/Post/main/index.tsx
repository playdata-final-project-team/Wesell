import { PostListItem } from 'types/interface';
import './style.css';
import { useEffect, useState } from 'react';
import PostList from 'components/PostList';

const MainBoard = () => {
  const [posts, setPosts] = useState<PostListItem[]>([]);

  // function: 게시글 목록 조회 함수//
  const fetchPosts = async () => {
    // axios 호출 추가 예정

    const data: PostListItem[] = [
      { id: '1', title: '게시글1', writer: '작성자1', hit: 1, wirteDate: '02.12' },
      { id: '2', title: '게시글2', writer: '작성자2', hit: 1, wirteDate: '02.12' },
      { id: '3', title: '게시글3', writer: '작성자3', hit: 5, wirteDate: '02.12' },
    ];

    setPosts(data);
  };

  useEffect(() => {
    fetchPosts();
  }, []);

  return (
    <div className="main-board-wrapper">
      <div className="main-board-container">
        <h2 className="main-board-title">게시판 명</h2>
        <PostList posts={posts} />
        <div>
          <div className="페이지네이션"></div>
          <button className="main-board-btn" type="button">
            글등록
          </button>
        </div>
      </div>
    </div>
  );
};

export default MainBoard;
