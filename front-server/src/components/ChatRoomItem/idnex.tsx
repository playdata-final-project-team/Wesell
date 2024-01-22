import './style.css';

interface Props {
  image: string;
  sellerNickname: string;
  currentMessage: string;
  currentSendDate: string;
}

// component: 채팅룸 항목 컴포넌트 //
const ChatRoomItem = (props: Props) => {
  const { currentMessage, image, sellerNickname, currentSendDate } = props;

  // render: 채팅룸 하목 컴포넌트 랜더링//
  return (
    <div className="chat-room-item-wrapper">
      <div className="chat-room-item-container">
        <div className="chat-room-item-image-box">
          <div style={{ backgroundImage: `url(${image})` }}></div>
        </div>
        <div className="chat-room-item-info-box">
          <div className="chat-room-item-title-box">
            <h2>{sellerNickname}</h2>
          </div>
          <div className="chat-room-item-content-box">
            <p className="current-chat-one">{currentMessage}</p>
            <p> - </p>
            <p className="chat-send-time">{currentSendDate}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatRoomItem;
