import { Link, useParams } from 'react-router-dom';
import './style.css';
import { useCallback, useEffect, useRef, useState } from 'react';
import { Client, IMessage } from '@stomp/stompjs';
import { ChatMessageResponseDto } from 'apis/response/chat';
import axios from 'axios';
import { ChatMessageRequestDto } from 'apis/request/chat';
import ChatMessageList from 'components/ChatMessageList';

// component: 채팅방 - 채팅 메시지 페이지 컴포넌트 //
const ChatMessagePage = () => {
  const { roomId } = useParams();
  // state: loading 상태값 - 목록 불러오는 동안 사용자 경험 개선을 위한 목적 //
  const [loading, setLoading] = useState<boolean>(true);

  // state: 채팅방을 이용할 클라이언트 상태값 //
  const [stompClient, setStompClient] = useState<Client | null>(null);

  // state: 채팅방 내 메시지 목록 상태값 //
  const [messages, setMessages] = useState<ChatMessageResponseDto[]>([]);

  // state: 메시지 작성자 상태값 //
  const [sender, setSender] = useState<string>('');

  // state: 메시지 작성 내용 상태값 //
  const [newMessage, setNewMessage] = useState<string>('');

  // state: 현재 채팅방 페이지 상태값 //
  const [currentPage, setCurrentPage] = useState<number>(1);

  // state: 채팅방 잔여 메시지 여부 상태값 //
  const [hasMore, setHasMore] = useState<boolean>(false);

  // function: 스크롤 맨 아래로 이동하는 함수 //
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollTo(0, messagesEndRef.current.scrollHeight);
  };

  // comment: useCallback 초기 채팅 메시지를 불러오는 함수 호출//
  const loadInitChatMessages = useCallback(async () => {
    try {
      const response = await axios.get(`/chat-service/api/v2/rooms/${roomId}/messages?size=10`);
      const responseMessages = response.data.content as ChatMessageResponseDto[];
      setMessages(responseMessages);
      setHasMore(responseMessages.length > 0);
      setLoading(false);
    } catch (error) {
      console.error('채팅 내역 로드 실패', error);
    }
  }, [roomId]);

  useEffect(() => {
    if (loading) {
      // 초기 채팅 메시지를 불러오는 함수 호출
      loadInitChatMessages();
    }
    // stompjs 라이브러리를 사용하여 새로운 웹소켓 클라이언트를 생성.
    // 클라이언트 관련 설정으로 웹소켓 서버 URL, 재연결 지연시간, 연결 성공 시 실행될 콜백 함수 등이 포함
    const client = new Client({
      brokerURL: 'ws://localhost:8788/chat', // 서버 WebSocket URL
      reconnectDelay: 5000,
      onConnect: () => {
        // client.subscribe() : 웹소켓 클라이언트가 특정 room에 구독을 시작하고, 해당 채팅방으로 메시지가
        // 수신되면 실행 될 콜백함수 정의. 콜백 함수에서는 수신된 메시지를 파싱하고 새로운 메시지를 기존
        // 메시지 배열의 앞쪽에 추가한다.
        client.subscribe(`/sub/${roomId}`, (message: IMessage) => {
          const msg: ChatMessageResponseDto = JSON.parse(message.body);
          setMessages((prevMessages) => [msg, ...prevMessages]);
        });
      },
    });
    // 웹소켓 클라이너트를 활성화하여 연결을 시작.
    client.activate();
    setStompClient(client);
    // useEffect 훅의 클린업 함수. 컴포넌트가 언마운트도히거나, useEffect의 의존성 배열에 있는값이 변경할
    // 경우 실행됨. 이 경우 웹소켓 클라이언트가 비활성화된다.(연결 종료)
    return () => {
      client.deactivate();
    };
  }, [currentPage, loadInitChatMessages, loading, roomId]);

  const fetchMessages = async () => {
    try {
      const response = await axios.get(
        `/chat-service/api/v2/rooms/${roomId}/messages?page=${currentPage}&size=10`,
      );
      const responseMessages = response.data.content as ChatMessageResponseDto[];
      setMessages([...messages, ...responseMessages]);
      setCurrentPage((prev) => prev + 1);
      setHasMore(responseMessages.length > 0);
      scrollToBottom();
    } catch (error) {
      console.error('채팅 내역 로드 실패', error);
    }
  };

  const sendMessage = () => {
    console.log('send');
    if (stompClient && newMessage) {
      const chatMessage: ChatMessageRequestDto = {
        sender: sender,
        message: newMessage,
        roomId: parseInt(roomId || ''),
      };
      stompClient.publish({
        destination: `/pub/rooms/${roomId}/send`,
        body: JSON.stringify(chatMessage),
      });
      setNewMessage('');
    }
  };

  return (
    <div className="chat-container">
      <div>
        <Link to={'/rooms'} className="back-link">
          뒤로 가기
        </Link>
      </div>
      <ChatMessageList
        messagesEndRef={messagesEndRef}
        messages={messages}
        fetchMessages={fetchMessages}
        hasMore={hasMore}
        sender={sender}
        newMessage={newMessage}
        sendMessage={sendMessage}
        setSender={setSender}
        setNewMessage={setNewMessage}
      />
    </div>
  );
};

export default ChatMessagePage;
