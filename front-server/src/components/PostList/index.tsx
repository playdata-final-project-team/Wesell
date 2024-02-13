import PostListItem from 'types/interface/post-list-item';
import './style.css';

interface Props {
  posts: PostListItem[];
}

const PostList = (props: Props) => {
  const { posts } = props;
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
              <td>{post.title}</td>
              <td>{post.writer}</td>
              <td>{post.click}</td>
              <td>{post.createdAt}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PostList;
