import { useEffect, useState } from 'react';
import './style.css';
import { loadChatRoomListRequest } from 'apis';
import { ChatRoomListResponseDto, ChatRoomResponseDto } from 'apis/response/chat';
import { Link } from 'react-router-dom';

const ChatRoomPage = () => {
  // state: 채팅방 목록 페이지
  const [chatRoomList, setChatRoomList] = useState<ChatRoomResponseDto[]>([]);

  useEffect(() => {
    const laodChatRoomHistory = async () => {
      const responseBody = await loadChatRoomListRequest(sessionStorage.getItem('uuid'));

      const successResponse = responseBody as ChatRoomListResponseDto;
      if (successResponse.content) {
        setChatRoomList(successResponse.content.roomList);
      }
    };

    laodChatRoomHistory();
  }, []);

  return (
    <div>
      <ul>
        {chatRoomList.map((chatRoom, idx) => (
          <div key={idx}>
            <li>
              <Link to={`/rooms/${chatRoom.roomId}`}>{chatRoom.seller}</Link>
            </li>
          </div>
        ))}
      </ul>
      <div>
        <Link to={'/test/rooms/create'} className="create-link">
          채팅방 만들기
        </Link>
      </div>
    </div>
  );
};

export default ChatRoomPage;
