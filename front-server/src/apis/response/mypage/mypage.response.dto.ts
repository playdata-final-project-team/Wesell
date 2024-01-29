import ResponseDto from '../response.dto';
export default interface MypageResponseDto extends ResponseDto {
  content: {
    name: string;
    nickname: string;
    phone: string;
    email: string;
  };
}
