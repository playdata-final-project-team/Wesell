import { Ref } from 'react';
import './style.css';
import { ChatMessageResponseDto } from 'apis/response/chat';
import InfiniteScroll from 'react-infinite-scroll-component';
import Loading from 'components/Loading';

interface Props {
  messagesEndRef: Ref<HTMLDivElement>;
  messages: ChatMessageResponseDto[];
  fetchMessages: () => Promise<void>;
  hasMore: boolean;
  sender: string;
  newMessage: string;
  sendMessage: () => void;
  setSender: (value: string) => void;
  setNewMessage: (value: string) => void;
}

const ChatMessageList = (props: Props) => {
  const { messagesEndRef, messages, fetchMessages, hasMore, sender, newMessage } = props;
  const { sendMessage, setSender, setNewMessage } = props;

  return (
    <>
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
          {messages.map((msg, index) => (
            <div key={msg.id}>
              {msg.id}=={msg.sender}: {msg.message}
            </div>
          ))}
        </InfiniteScroll>
      </div>
      <div className="input-group">
        <label>작성자</label>
        <input
          type="text"
          value={sender}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setSender(e.target.value)}
        />
      </div>
      <div className="input-group">
        <input
          type="text"
          value={newMessage}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) => setNewMessage(e.target.value)}
        />
        <button className="send-button" onClick={sendMessage}>
          Send
        </button>
      </div>
    </>
  );
};

export default ChatMessageList;
