import ResponseDto from '../response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface SignInResponseDto extends ResponseDto {
  content: {
    uuid: string;
    role: string;
    kakaoId: number;
  };
}
