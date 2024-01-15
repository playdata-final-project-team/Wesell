import ResponseDto from '../response.dto';
import ChatMessageResponseDto from './chat-message.response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface ChatRoomResponseDto extends ResponseDto {
  roomId: number;
  seller: string;
  chatMessageList?: ChatMessageResponseDto[];
}
