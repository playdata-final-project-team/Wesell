import ResponseDto from '../response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface SignInResponseDto extends ResponseDto {
  uuid: string;
  role: string;
  kakaoId: number;
}
