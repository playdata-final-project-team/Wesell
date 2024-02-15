import PostListItem from 'types/interface/post-list-item';
import './style.css';
import { useNavigate } from 'react-router-dom';
interface Props {
  posts: PostListItem[];
  boardId: string | undefined;
}

const PostList = (props: Props) => {
  const { posts, boardId } = props;
  const navigator = useNavigate();
  return (
    <div className="post-list-box">
      <table className="post-list">
        <thead>
          <tr className="post-list-thead">
            <th>번호</th>
            <th>제목</th>
            <th>글쓴이</th>
            <th>조회</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          {/* 게시글 목록 */}
          {posts.map((post, index) => (
            <tr className="post-list-tbody" key={index}>
              <td>{post.id}</td>
              <td
                id="link-to-detail"
                onClick={(e) => {
                  navigator(`/board/${boardId}/${post.id}`);
                  e.currentTarget.classList.add('visited');
                }}
              >
                {post.title}
              </td>
              <td>{post.writer}</td>
              <td>{post.click}</td>
              <td>{post.createdAt}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {posts.length === 0 && (
        <div style={{ marginTop: '10px', display: 'flex', justifyContent: 'center' }}>
          <span style={{ fontSize: '20px' }}>작성된 게시글이 없습니다.</span>
        </div>
      )}
    </div>
  );
};

export default PostList;
