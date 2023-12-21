import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { SignInResponseDto } from './response/auth';
import { ResponseDto } from './response';
import ResponseCode from 'constant/response-code.enum';
import MypageResponseDto from './response/mypage/mypage.response.dto';
import { PwCheckRequestDto } from './request/delete';
import MyDealListWithPageResponseDto from './response/mypage/my.deal-list-page.response.dto';

const SIGN_IN_URL = () => '/auth-server/api/v1/sign-in';
const SIGN_UP_URL = () => '/auth-server/api/v1/sign-up';
const LOGOUT_URL = () => '/auth-server/api/v1/logout';
const NICKNAME_CHECK_URL = () => '/user-service/api/v1/dup-check';
const PHONE_CHECK_URL = () => '/auth-service/api/v1/phone/validate';
const KAKAO_CALLBACK_URL = () => '/auth-server/api/v1/kakao/auth-code';
const REFRESH_TOKEN_URL = () => '/auth-server/api/v1/refresh';
const MYPAGE_URL = (uuid : string|null) => `/user-service/api/v1/feign/mypage/${uuid}`;
const MY_INFO_UPDATE_URL = (uuid: string) => `/user-service/api/v1/${uuid}`;
const PW_CHECK_URL = () => '/auth-server/api/v1/delete/pw-check';
const DELETE_URL = (uuid: string | null) => `/auth-server/api/v1/delete/${uuid}`;
const MY_DEAL_LIST_URL = () => '/deal-service/api/v1/list';

export const signInRequest = async (requestBody: SignInRequestDto) => {
  try {
    const response = await axios.post(SIGN_IN_URL(), requestBody);
    const responseBody: SignInResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: SignInResponseDto = error.response.data;
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

export const logoutRequest = async () => {
  try{
    const response = await axios.get(LOGOUT_URL());
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error : any){
    if(!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const mypageInfoRequest = async (uuid:string|null) => {
  try{
    const response = await axios.get(MYPAGE_URL(uuid));
    const responseBody: MypageResponseDto = response.data;
    return responseBody;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error : any){
    if(!error.response) return null;
    const responseBody: MypageResponseDto = error.response.data;
    return responseBody;
  }
};

export const myInfoUpdateRequest = async (uuid: string) => {
  try{
    const response = await axios.put(MY_INFO_UPDATE_URL(uuid));
    const responseBody: ResponseDto = response.data;
    return responseBody;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error: any){
    if(!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const pwCheckRequest = async (requestDto: PwCheckRequestDto) => {
  try{
    const response = await axios.post(PW_CHECK_URL(),requestDto);
    const responseBody : ResponseDto = response.data;
    return responseBody
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error: any){
    if(!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
}

export const deleteUserRequest= async (uuid: string | null) => {
  try{
    const response = await axios.delete(DELETE_URL(uuid));
    const responseBody : ResponseDto = response.data;
    return responseBody
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error: any){
    if(!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
}

export const myDealListRequest = async (uuid: string | null, page: number) => {
  try{
    const response= await axios.get(MY_DEAL_LIST_URL(),{
      params:{uuid: uuid, page: page}
    });
    const responseBody: MyDealListWithPageResponseDto = response.data;
    return responseBody;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error:any){
    if(!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

export const GET_SING_IN_USER_URL = ()=> `/auth-server/api/v1/user`;
const getSignInUserRequest = async ()=> {
  try{
    const response = await axios.get(GET_SING_IN_USER_URL());
    const responseBody = response.data;
    return responseBody;
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  }catch(error:any){
    if(error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
}

axios.interceptors.response.use(
    (res) => res,
    async (err) => {

      const {config, response: {status}} = err;

      if(config.url === REFRESH_TOKEN_URL || status !== 401 || config.sent){
        return Promise.reject(err);
      }

      config.sent = true;
      const responseBody : ResponseDto | null = await refreshTokenRequest();

      if (!responseBody) {
        alert('네트워크 연결 상태를 확인해주세요!');
        return;
      }

      if(responseBody.code === ResponseCode.INVALID_REFRESH_TOKEN ||
        responseBody.code === ResponseCode.INVALID_ACCESS_TOKEN){
          await logoutRequest();
          window.location.href=`http://localhost:3000/auth`;// 로그인 페이지로 이동
      }

      return axios(config); // 재요청
    }
);