import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { SignInResponseDto } from './response/auth';
import { ResponseDto } from './response';

const DOMAIN = 'http://localhost:3000';

const API_DOMAIN = `${DOMAIN}/api/v1`;

const SIGN_IN_URL = () => `${API_DOMAIN}/auth-server/sign-in`;
const SIGN_UP_URL = () => `${API_DOMAIN}/auth-server/sign-up`;
const NICKNAME_CHECK_URL = () => `${API_DOMAIN}/user-service/dup-check`;

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

// eslint-disable-next-line @typescript-eslint/no-empty-function
export const signUpRequest = async (requestBody: SignUpRequestDto) => {};
