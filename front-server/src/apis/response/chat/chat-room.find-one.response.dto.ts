import ResponseDto from '../response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface FineOneResponseDto extends ResponseDto {
  content: {
    roomId: number;
  };
}
