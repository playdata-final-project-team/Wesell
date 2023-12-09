export default interface SignUpRequestDto {
  name: string;
  nickname: string;
  phone: string;
  email: string;
  pw: string;
  pwRe: string;
  agree: boolean;
}
