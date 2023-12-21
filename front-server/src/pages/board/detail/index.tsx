import React from "react";
import { useEffect, useState } from "react";
import { useParams } from 'react-router-dom';

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

interface PostDetailPageProps {
  postId: number;
}

function PostDetailPage() {
  const { postId } = useParams();
  const [post, setPost] = useState<PostJson|null>(null);

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
    <div className="container">

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
  );
}

export default PostDetailPage;


