import { useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import './index.css'

interface PostJson {
  "uuid":string;
  "postId":number;
  "title":string;
  "createdAt":string;
  "price":number;
  "detail":string;
  "link":string;
  "nickname":string;
  "imageUrl":string;  
}

function PostDetailPage() {
  const { postId } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState<PostJson|null>(null);
  const [isLoaded, setIsLoaded] = useState(false);

  const moveToUpdate = () => {
    navigate('/board/edit/' + postId);
  };

  useEffect(() => {
    console.log(postId);

    const POST_URL = `/deal-service/api/v1/post?id=${postId}`;
    console.log("------url--------");
    fetch(POST_URL, {
    method: "GET"
    })
    .then(response => response.json())
    .then(json => setPost(json))
    .then(() => setIsLoaded(true));

  },[]);

  return (
    <>
    <div className="board-wrapper">
    <div className="board-body">
      {post && (
        <div className="board-image">
          <img src={post.imageUrl} className="image" />
        </div>)}
      {post && (
        <div className="board-content">
          <h2>{post.title}</h2>
          <p className="createdAt">{post.createdAt}</p>
          <p className="nickname">{post.nickname}</p>
          <p className="price">{post.price}</p>
          <p className="detail">{post.detail}</p>
          <p>오픈 카카오톡: <a href={post.link} target="_blank" rel="noopener noreferrer">{post.link}</a></p>
        </div>
      )}
      <div className="update-button-wrapper">
    {
      window.sessionStorage.getItem("uuid") === post?.uuid &&
      <button className="update-button" onClick={moveToUpdate}>수정하기</button>
    }
    </div>
    </div>
    
    </div>
</>
  );
}

export default PostDetailPage;


