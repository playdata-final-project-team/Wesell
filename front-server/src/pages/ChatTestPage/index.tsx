import { ChatRoomRequestDto } from 'apis/request/chat';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

const ChatTestPage = () => {
  const [seller, setSeller] = useState<string>('');
  const [consumer, setConsumer] = useState<string>('');
  const [productId, setProductId] = useState<number>(0);
  const navigator = useNavigate();

  useEffect(() => {
    const uuid = sessionStorage.getItem('uuid');
    if (uuid) {
      setConsumer(uuid);
    }
  }, [consumer]);

  const buttonClickHandler = async () => {
    const body: ChatRoomRequestDto = {
      consumer: consumer,
      seller: seller,
      productId: productId,
    };

    if (consumer === '') {
      console.error('구매자 uuid 입력하세유');
      return;
    }

    if (seller === '') {
      console.error('판매자 닉네임 입력하세유');
      return;
    }

    if (productId === 0) {
      console.error('판매품ID 입력하세유');
      return;
    }

    try {
      await axios.post('/chat-service/api/v2/rooms', body);

      navigator('/rooms');
    } catch (error: any) {
      console.error('😒 채팅 관련 서버 오류 발생', error);
    }
  };

  return (
    <div>
      <div className="input-group">
        <label>구매자 uuid</label>
        <input
          disabled={true}
          type="text"
          value={consumer}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setConsumer(e.target.value)}
        />
      </div>
      <div className="input-group">
        <label>판매자 닉네임</label>
        <input
          type="text"
          value={seller}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setSeller(e.target.value)}
        />
      </div>
      <div className="input-group">
        <label>판매품 id</label>
        <input
          type="text"
          value={productId}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setProductId(parseInt(e.target.value || ''))
          }
        />
      </div>

      <button type={'button'} onClick={buttonClickHandler}>
        채팅방 만들기
      </button>
    </div>
  );
};

export default ChatTestPage;
