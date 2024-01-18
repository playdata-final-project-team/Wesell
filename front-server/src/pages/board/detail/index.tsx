import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './index.css';
import axios from 'axios';

interface PostJson {
  uuid: string;
  postId: number;
  categoryId: number;
  title: string;
  createdAt: string;
  price: number;
  detail: string;
  nickname: string;
  dealCount: number;
  imageUrl: string;
}

function PostDetailPage() {
  const { postId } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState<PostJson | null>(null);
  const [isLoaded, setIsLoaded] = useState(false);

  const moveToUpdate = () => {
    navigate('/product/update/' + postId);
  };

  const moveToPay = () => {
    navigate('/payment/'+postId);
  }

  useEffect(() => {
    console.log(postId);

    const POST_URL = `/deal-service/api/v2/post?id=${postId}`;
    console.log('------url--------');
  axios.get(POST_URL) 
  .then((response) => setPost(response.data))
  .then(()=>setIsLoaded(true));
  console.log(post);
  }, []);
  

  return (
    <>
      <div className="board-wrapper">
        <div className="board-body">
          {post && (
            <div className="board-image">
              <img src={post.imageUrl} className="image" />
            </div>
          )}
          {post && (
            <div className="board-content">
              <h2>{post.title}</h2>
              {
                post.dealCount < 6 &&
                <button className="safe-button">안전</button>
                }
              <p className="createdAt">{post.createdAt}</p>
              <p className="nickname">{post.nickname}</p>
              <p className="price">가격: {post.price}</p>
              <p className="detail">설명: {post.detail}</p>
            </div>
          )}
          <div className="update-button-wrapper">
            {
               window.sessionStorage.getItem('uuid') === post?.uuid &&
              <button className="update-button" onClick={moveToUpdate}>
                수정하기
              </button>
           }
           {window.sessionStorage.getItem('uuid') !== post?.uuid &&
              <button className="update-button" onClick={moveToPay}>
              구매하기
            </button>}
          </div>
        </div>
      </div>
    </>
  );
}

export default PostDetailPage;
