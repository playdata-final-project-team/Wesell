import { useCallback, useEffect, useRef, useState } from 'react';
import './style.css';
import { loadChatRoomListRequest } from 'apis';
import {
  ChatMessage,
  ChatMessageResponseDto,
  ChatRoomListResponseDto,
  ChatRoomResponseDto,
} from 'apis/response/chat';
import ChatRoomList from 'components/ChatRoomList';
import ChatMessageList from 'components/ChatMessageList';
import { ChatMessageRequestDto } from 'apis/request/chat';
import axios from 'axios';
import { Client, IMessage } from '@stomp/stompjs';
import { useParams } from 'react-router-dom';
import ReactModal from 'react-modal';

// component: 채팅하기 페이지 컴포넌트 //
const ChatRoomPage = () => {
  // component: 채팅 룸 목록 카드 컴포넌트 //
  const ChatRoomListCard = () => {
    // state: 채팅방 목록 페이지
    const [chatRoomList, setChatRoomList] = useState<ChatRoomResponseDto[]>([]);

    useEffect(() => {
      loadChatRoomList();
    }, []);

    // function: 채팅방 목록 조회 함수//
    const loadChatRoomList = async () => {
      const responseBody = await loadChatRoomListRequest(sessionStorage.getItem('uuid'));

      const successResponse = responseBody as ChatRoomListResponseDto;
      if (successResponse) {
        setChatRoomList(successResponse.content.roomList);
      }
    };

    return <ChatRoomList chatRooms={chatRoomList} />;
  };

  // component: 채팅 메시지 목록 카드 컴포넌트 //
  const ChatMessageListCard = () => {
    // params: 채팅룸 번호 //
    const { roomId } = useParams();

    // session-storage: 전송자 uuid//
    const sender = sessionStorage.getItem('uuid');

    // state: 채팅방을 이용할 클라이언트 상태값 //
    const [stompClient, setStompClient] = useState<Client | null>(null);

    // state: 채팅방 내 메시지 목록 상태값 //
    const [messages, setMessages] = useState<ChatMessage[]>([]);

    // state: 메시지 작성 내용 상태값 //
    const [newMessage, setNewMessage] = useState<string>('');

    // state: 현재 채팅방 페이지 상태값 //
    const [currentPage, setCurrentPage] = useState<number>(1);

    // state: 채팅방 잔여 메시지 여부 상태값 //
    const [hasMore, setHasMore] = useState<boolean>(false);

    // state: 탈퇴하기 팝업창 상태값 //
    const [closePopup, setClosePopup] = useState<boolean>(true);

    // function: 스크롤 맨 아래로 이동하는 함수 //
    const messagesEndRef = useRef<HTMLDivElement>(null);
    const scrollToBottom = () => {
      messagesEndRef.current?.scrollTo(0, messagesEndRef.current.scrollHeight);
    };

    // comment: useCallback 초기 채팅 메시지를 불러오는 함수 호출//
    const loadInitChatMessages = useCallback(async () => {
      try {
        const response = await axios.get(`/chat-service/api/v2/rooms/${roomId}/messages?size=10`);
        const responseMessages = response.data.content as ChatMessageResponseDto;
        setMessages(responseMessages.messages);
        setHasMore(messages.length > 0);
      } catch (error) {
        console.error('⚠️ 채팅 내역 로드 실패', error);
      }
    }, [roomId]);

    // effect: 구독하기 및 채팅 메시지 목록 조회 //
    useEffect(() => {
      // 채팅방 목록만 나오는 경우, 요청을 막기위한 early return
      if (!roomId) {
        return;
      }
      // 초기 채팅 메시지를 불러오는 함수 호출
      loadInitChatMessages();

      // stompjs 라이브러리를 사용하여 새로운 웹소켓 클라이언트를 생성.
      // 클라이언트 관련 설정으로 웹소켓 서버 URL, 재연결 지연시간, 연결 성공 시 실행될 콜백 함수 등이 포함
      const client = new Client({
        brokerURL: 'ws://localhost:8788/chat', // 서버 WebSocket URL
        reconnectDelay: 5000,
        onConnect: () => {
          // client.subscribe() : 웹소켓 클라이언트가 특정 room에 구독을 시작하고, 해당 채팅방으로 메시지가
          // 수신되면 실행 될 콜백함수 정의. 콜백 함수에서는 수신된 메시지를 파싱하고 새로운 메시지를 기존
          // 메시지 배열의 앞쪽에 추가한다.
          client.subscribe(`/sub/chat/${roomId}`, (message: IMessage) => {
            const msg: ChatMessage = JSON.parse(message.body);
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
    }, [roomId, currentPage]);

    // function: //
    const fetchMessages = async () => {
      try {
        const response = await axios.get(
          `/chat-service/api/v2/rooms/${roomId}/messages?page=${currentPage}&size=10`,
        );
        const responseMessages = response.data.content as ChatMessageResponseDto;
        const chatMessages = responseMessages.messages;
        setMessages([...messages, ...chatMessages]);
        setCurrentPage((prev) => prev + 1);
        setHasMore(chatMessages.length > 0);
        scrollToBottom();
      } catch (error) {
        console.error('⚠️ 채팅 내역 로드 실패', error);
      }
    };

    // function: 메시지 전송 함수//
    const sendMessage = () => {
      console.log('send');
      if (stompClient && newMessage) {
        const chatMessage: ChatMessageRequestDto = {
          sender: sender,
          message: newMessage,
          roomId: roomId,
        };
        stompClient.publish({
          destination: `/pub/chat/message`,
          body: JSON.stringify(chatMessage),
        });
        setNewMessage('');
      }
    };

    // function: 채팅방 관련 옵션 모달 페이지
    const leaveChatRoom = () => {
      setClosePopup(false);
    };

    // event-handler: 채팅방 나가기 버튼 이벤트 처리 //
    const onChatRoomLeaveBtnClickHandler = () => {
      //
      try {
        axios.delete(`/chat-service/api/v2/rooms?roomId=${roomId}&demander=${sender}`);
      } catch (error) {
        console.error('☢️ 채팅방 나가기 실패', error);
      }
    };

    return (
      <div className="chat-message-list-wrapper">
        {roomId && (
          <ChatMessageList
            onLeaveChatRoomClick={leaveChatRoom}
            messagesEndRef={messagesEndRef}
            messages={messages}
            fetchMessages={fetchMessages}
            hasMore={hasMore}
            // 판매자 이름 어떻게 가져와야 하지..
            seller={'판매자 이름'}
            newMessage={newMessage}
            sendMessage={sendMessage}
            setNewMessage={setNewMessage}
          />
        )}
        <ReactModal
          overlayClassName={'modal-overlay'}
          className={'modal-content'}
          isOpen={!closePopup}
          onRequestClose={() => {
            setClosePopup(true);
          }}
          ariaHideApp={false}
          contentLabel={'Pop up Message'}
          shouldCloseOnOverlayClick={true}
        >
          <div className="close-box">
            <div className="close-confirm-comment">
              <h2 style={{ color: 'red' }}> 정말 채팅방을 나가시겠습니까? </h2>
              <p>
                대화방을 나가면 대봐 내용이 모두 삭제됩니다. <br />
                대화방을 나가시겠습니까?
              </p>
            </div>
            <div className="close-confirm-button">
              <button style={{ color: 'red' }} onClick={onChatRoomLeaveBtnClickHandler}>
                예
              </button>
              <button
                onClick={() => {
                  setClosePopup(true);
                }}
              >
                아니오
              </button>
            </div>
          </div>
        </ReactModal>
      </div>
    );
  };

  // render: 채팅하기 페이지 컴포넌트 랜더링 //
  return (
    <div className="chat-page-wrapper">
      <div className="chat-page-container">
        <div className="chat-room-list-card">
          <ChatRoomListCard />
        </div>
        <div className="chat-message-list-card">
          <ChatMessageListCard />
        </div>
      </div>
    </div>
  );
};

export default ChatRoomPage;
