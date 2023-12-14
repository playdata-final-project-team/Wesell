import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { SignInResponseDto } from './response/auth';
import { ResponseDto } from './response';

const SIGN_IN_URL = () => '/auth-server/api/v1/sign-in';
const SIGN_UP_URL = () => '/auth-server/api/v1/sign-up';
const NICKNAME_CHECK_URL = () => '/user-service/api/v1/dup-check';
const PHONE_CHECK_URL = () => '/auth-service/api/v1/phone/validate';
const KAKAO_CALLBACK_URL = () => '/auth-server/api/v1/kakao/auth-code';
const REFRESH_TOKEN_URL = () => '/auth-server/api/v1/refresh';

export const signInRequest = async (requestBody: SignInRequestDto) => {
  try {
    console.log(requestBody);
    const response = await axios.post(SIGN_IN_URL(), requestBody);
    const responseBody: SignInResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const nicknameDupCheckRequest = async (param: string) => {
  try {
    const response = await axios.get(NICKNAME_CHECK_URL(), {
      params: { nickname: param },
    });
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const phoneValidateRequest = async (param: string) => {
  try{
    const response = await axios.get(PHONE_CHECK_URL(),{
      params: {phone : param}
    });
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error : any){
    if(!error.response) return null;
    const responseBody : ResponseDto = error.response.data;
    return responseBody;
  }
}

export const signUpRequest = async (requestBody: SignUpRequestDto) => {
  try {
    const response = await axios.post(SIGN_UP_URL(), requestBody);
    const responseBody:ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const kakaoCallbackAuthCodeRequest = async (authCode: string | null) => {
  try {
    const response = await axios.post(KAKAO_CALLBACK_URL(), authCode);
    const resonseBody: ResponseDto = response.data;
    return resonseBody;

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const refreshTokenRequest = async () =>{
  try{
    const response = await axios.get(REFRESH_TOKEN_URL());
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error: any){
    if(!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};