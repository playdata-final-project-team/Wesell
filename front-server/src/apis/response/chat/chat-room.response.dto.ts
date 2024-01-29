import ResponseDto from '../response.dto';
import ChatMessageResponseDto from './chat-message.response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface ChatRoomResponseDto extends ResponseDto {
  roomId: string;
  seller: string;
  lastSendDate: string;
  lastSendMessage: string;
  imageUrl: string;
  chatMessageList?: ChatMessageResponseDto[];
}
