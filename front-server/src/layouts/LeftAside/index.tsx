import axios from 'axios';
import './style.css';
import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';

interface Board {
  id: number;
  title: string;
}

export default function LeftAside() {
  const [boards, setBoards] = useState<Board[]>([]);
  const fetchBoardList = async () => {
    try {
      const response = await axios.get('/board-service/api/v1/board');
      const boardList = response.data as Board[];
      setBoards(boardList);
    } catch (error) {
      console.error('☢️ 게시글 목록 조회 중 오류 발생', error);
    }
  };

  useEffect(() => {
    fetchBoardList();
  }, []);
  return (
    <div className="left-aside-wrapper">
      <div className="left-aside-container">
        <div className="board-list-icon-box">
          <div className="board-list-icon"></div>
        </div>
        {/* 게시판 목록 조회 */}
        <ul className="aside-board-list">
          {boards.map((board, index) => (
            <li key={index}>
              <Link to={`/board/${board.id}`} className="board-list-li">
                {board.title}
              </Link>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
}
