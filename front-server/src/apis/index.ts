import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { SignInResponseDto, SignUpResponseDto } from './response/auth';
import { ResponseDto } from './response';

const DOMAIN = 'http://localhost:8000';

const API_DOMAIN = `${DOMAIN}/auth-server/api/v1`;

const SIGN_IN_URL = () => `${API_DOMAIN}/sign-in`;
const SIGN_UP_URL = () => `${API_DOMAIN}/sign-up`;
const NICKNAME_CHECK_URL = () => `${API_DOMAIN}/dup-check`;

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
