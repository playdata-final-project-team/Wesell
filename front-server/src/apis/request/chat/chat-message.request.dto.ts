export default interface ChatMessageRequestDto {
  roomId: string | undefined;
  sender: string | null;
  message: string;
}
