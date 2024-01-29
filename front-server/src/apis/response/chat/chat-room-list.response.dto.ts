import ResponseDto from '../response.dto';
import ChatRoomResponseDto from './chat-room.response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface ChatRoomListResponseDto extends ResponseDto {
  content: {
    roomList: ChatRoomResponseDto[];
    hasNext: boolean;
  };
}
