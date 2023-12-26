import { useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import './index.css'

interface PostJson {
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
  useEffect(() => {
    const uuid = window.sessionStorage.getItem("uuid");
}, []);
  const { postId } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState<PostJson|null>(null);

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
    .then(json => setPost(json));

  },[]);

  return (
    <><div className="container">
      {post && (
        <div className="image-container">
          <img src={post.imageUrl} className="image" />
        </div>)}
      {post && (
        <div className="details-container">
          <h2>제목{post.title}</h2>
          <p>작성일: {post.createdAt}</p>
          <p>닉네임: {post.nickname}</p>
          <p>가격: {post.price}</p>
          <p>설명: {post.detail}</p>
          <p>링크: <a href={post.link} target="_blank" rel="noopener noreferrer">{post.link}</a></p>
        </div>
      )}
    </div>
    uuid === post.uuid &&
    <div className="edit-button">
      <button onClick={moveToUpdate}>수정하기</button>
    </div>
</>
  );
}

export default PostDetailPage;


