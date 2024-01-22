import { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './index.css';
import axios from 'axios';
import { ChatRoomRequestDto } from 'apis/request/chat';
import { CreateChatRoomResponseDto } from 'apis/response/chat';

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

function TestDetailPage() {
  const { productId } = useParams();
  const consumer = sessionStorage.getItem('uuid');
  const navigate = useNavigate();
  const [post, setPost] = useState<PostJson | null>(null);
  const [isLoaded, setIsLoaded] = useState(false);

  const moveToUpdate = () => {
    navigate('/product/update/' + productId);
  };

  const moveToPay = () => {
    navigate('/payment/' + productId);
  };

  // event-handler: 채팅하기 버튼 이벤트 처리//
  const moveToChat = async () => {
    if (!consumer) {
      alert('😒 로그인 후 이용바랍니다.');
      return;
    }

    const requestBody: ChatRoomRequestDto = {
      consumer: consumer,
      productId: parseInt(productId || ''),
      seller: post?.nickname,
    };

    try {
      const response = await axios.post('/chat-service/api/v2/rooms', requestBody);
      const roomId = response.data.content as CreateChatRoomResponseDto;

      if (roomId) {
        navigate(`/rooms/${roomId}`);
      } else {
        throw 'roomId가 없습니다.';
      }
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      console.error('⚠️ 채팅방 만들기 요청 실패', error);
    }
  };

  useEffect(() => {
    console.log(productId);

    const POST_URL = `/deal-service/api/v2/post?id=${productId}`;
    axios
      .get(POST_URL)
      .then((response) => setPost(response.data))
      .then(() => setIsLoaded(true));
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
              {post.dealCount < 6 && <button className="safe-button">안전</button>}
              <p className="createdAt">{post.createdAt}</p>
              <p className="nickname">{post.nickname}</p>
              <p className="price">가격: {post.price}</p>
              <p className="detail">설명: {post.detail}</p>
            </div>
          )}
          <div className="update-button-wrapper">
            {window.sessionStorage.getItem('uuid') === post?.uuid && (
              <button className="update-button" onClick={moveToUpdate}>
                수정하기
              </button>
            )}
            {window.sessionStorage.getItem('uuid') !== post?.uuid && (
              <div className="button-box">
                <button className="update-button" onClick={moveToPay}>
                  구매하기
                </button>
                <button className="chat-button" onClick={moveToChat}>
                  채팅하기
                </button>
              </div>
            )}
          </div>
        </div>
      </div>
    </>
  );
}

export default TestDetailPage;
