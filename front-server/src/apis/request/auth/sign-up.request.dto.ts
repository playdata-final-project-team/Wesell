export default interface SignUpRequestDto {
  name: string;
  nickname: string;
  phone: string;
  email: string;
  password: string;
  passwordCheck: string;
  agree: boolean;
}
