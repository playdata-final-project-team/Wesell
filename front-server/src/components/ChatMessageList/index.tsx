import { Ref } from 'react';
import './style.css';
import { ChatMessage } from 'apis/response/chat';
import InfiniteScroll from 'react-infinite-scroll-component';
import Loading from 'components/Loading';
import { FaArrowAltCircleUp } from 'react-icons/fa';
import { IoMdCloseCircle } from 'react-icons/io';

interface Props {
  messagesEndRef: Ref<HTMLDivElement>;
  messages: ChatMessage[];
  fetchMessages: () => Promise<void>;
  hasMore: boolean;
  seller: string;
  newMessage: string;
  sendMessage: () => void;
  setNewMessage: (value: string) => void;
  onLeaveChatRoomClick: () => void;
}

const ChatMessageList = (props: Props) => {
  const { messagesEndRef, messages, fetchMessages, hasMore, seller, newMessage } = props;
  const { sendMessage, setNewMessage, onLeaveChatRoomClick } = props;
  const uuid = sessionStorage.getItem('uuid');

  return (
    <div className="chat-message-list-wrapper">
      <div className="chat-message-list-container">
        <div className="chat-mesage-list-head">
          <h1 className="chat-message-list-title">{seller}</h1>
          <div className="chat-message-list-options" onClick={onLeaveChatRoomClick}>
            <IoMdCloseCircle size="20px" className="close-button" />
          </div>
        </div>
        <div id="scrollableDiv" className="chat-messages" ref={messagesEndRef}>
          <InfiniteScroll
            dataLength={messages.length}
            next={fetchMessages}
            style={{ display: 'flex', flexDirection: 'column-reverse' }}
            hasMore={hasMore}
            loader={<Loading />}
            inverse={true} // 스크롤을 위로 올릴 때 데이터 로드
            scrollableTarget="scrollableDiv"
          >
            {uuid &&
              messages.map((msg, index) => (
                <div key={index}>
                  <div className={uuid.match(msg.sender) ? 'my-message-box' : 'your-message-box'}>
                    <p>
                      {msg.content} - {msg.sendDate}
                    </p>
                  </div>
                </div>
              ))}
          </InfiniteScroll>
        </div>
        <div className="input-group">
          <div className="input-border-line">
            <textarea
              placeholder="메시지를 입력하세요"
              value={newMessage}
              onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) =>
                setNewMessage(e.target.value)
              }
            ></textarea>
            <button className="send-button" onClick={sendMessage}>
              <FaArrowAltCircleUp size={'20px'} className="element-style" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ChatMessageList;
