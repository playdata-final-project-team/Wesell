import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { SignInResponseDto, SignUpResponseDto } from './response/auth';
import { ResponseDto } from './response';

const AUTH_DOMAIN = 'http://localhost:8000/auth-server';

const API_DOMAIN = `${AUTH_DOMAIN}/api/v1`;

const SIGN_IN_URL = () => `${API_DOMAIN}/sign-in`;
const SIGN_UP_URL = () => `${API_DOMAIN}/sign-up`;
const NICKNAME_CHECK_URL = () => `${API_DOMAIN}/dup-check`;
const KAKAO_CALLBACK_URL = () => `${API_DOMAIN}/kakao/auth-code`;

export const signInRequest = async (requestBody: SignInRequestDto) => {
  try {
    const response = await axios.post(SIGN_IN_URL(), requestBody);
    const responseBody: SignInResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response.data) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const nicknameDupCheckRequest = async (nickname: string) => {
  try {
    const response = await axios.post(NICKNAME_CHECK_URL(), nickname);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response.data) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const signUpRequest = async (requestBody: SignUpRequestDto) => {
  try {
    const response = await axios.post(SIGN_UP_URL(), requestBody);
    const responseBody: SignUpResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response.data) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// comment: 인가 코드 인증 서버로 보내기 //
export const kakaoCallbackAuthCodeRequest = async (authCode : string | null) => {
  try{
    const response = await axios.post(KAKAO_CALLBACK_URL(),authCode);
    const resonseBody: ResponseDto = response.data;
    return resonseBody;

  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error: any){
    if(!error.response.data) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};