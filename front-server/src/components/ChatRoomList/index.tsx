import ChatRoomItem from 'components/ChatRoomItem/idnex';
import './style.css';
import { Link } from 'react-router-dom';

interface ChatRoomResponseDto {
  roomId: string;
  seller: string;
  lastSendDate: string;
  lastSendMessage: string;
  imageUrl: string;
}

interface Props {
  chatRooms?: ChatRoomResponseDto[];
}

const ChatRoomList = (props: Props) => {
  const { chatRooms } = props;
  return (
    <div className="chat-room-list-wrapper">
      <div className="chat-room-list-container">
        <h1>전체 채팅방</h1>
        <div className="chat-room-list">
          <ul>
            {chatRooms &&
              chatRooms.map((chatRoom, idx) => (
                <div key={idx}>
                  <li>
                    <Link to={`/rooms/${chatRoom.roomId}`}>
                      <ChatRoomItem
                        image={chatRoom.imageUrl}
                        currentMessage={chatRoom.lastSendMessage}
                        currentSendDate={chatRoom.lastSendDate}
                        sellerNickname={chatRoom.seller}
                      ></ChatRoomItem>
                    </Link>
                  </li>
                </div>
              ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default ChatRoomList;
