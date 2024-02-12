import axios from 'axios';
import { SignInRequestDto, SignUpRequestDto } from './request/auth';
import { PhoneValdateResponseAtSignUpDto, SignInResponseDto } from './response/auth';
import { ResponseDto } from './response';
import MypageResponseDto from './response/mypage/mypage.response.dto';
import { PwCheckRequestDto } from './request/delete';
import MyDealListWithPageResponseDto from './response/mypage/my.deal-list-page.response.dto';
import { DealInfoStatusUpdateRequestDto, MypageUpdateRequestDto } from './request/mypage';
import PhoneValidateResponseDto from './response/auth/phone-validate.response.dto';
import { ChatRoomListResponseDto } from './response/chat';

const SIGN_IN_URL = () => '/auth-server/api/v2/sign-in';
const SIGN_UP_URL = () => '/auth-server/api/v2/sign-up';
const LOGOUT_URL = () => '/auth-server/api/v2/logout';
const KAKAO_LOGOUT_URL = () => `/auth-server/api/v2/kakao/logout`;
const SIGN_UP_PHONE_CHECK_URL = () => '/auth-service/api/v2/sign-up/phone/validate';
const PHONE_CHECK_URL = () => '/auth-service/api/v2/phone/validate';
const KAKAO_CALLBACK_URL = () => '/auth-server/api/v2/kakao/auth-code';
const REFRESH_TOKEN_URL = () => '/auth-server/api/v2/refresh';
const PW_CHECK_URL = () => '/auth-server/api/v2/delete/pw-check';
const DELETE_URL = (uuid: string | null) => `/auth-server/api/v2/delete/${uuid}`;
const KAKAO_DELETE_URL = () => '/auth-server/api/v2/delete/kakao';

const NICKNAME_CHECK_URL = () => '/user-service/api/v2/dup-check';
const MYPAGE_URL = (uuid: string | null) => `/user-service/api/v2/users/${uuid}`;
const MY_INFO_UPDATE_URL = (uuid: string | null) => `/user-service/api/v2/users/${uuid}`;

const MY_DEAL_LIST_URL = () => '/deal-service/api/v2/list';
const SALESTATUS_CHANGE_URL = () => '/deal-service/api/v2/complete';
const DELETE_POSTLIST_URL = () => '/deal-service/api/v2/checked/delete';

const CHAT_ROOM_LIST = () => '/chat-service/api/v2/rooms';

// api request : 로그인 요청 o
export const signInRequest = async (requestBody: SignInRequestDto) => {
  try {
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

// api request : 닉네임 중복 조회 요청 o
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

// api request : 회원가입 시 휴대폰 인증 요청 o
export const phoneValidateAtSignUpRequest = async (param: string) => {
  try {
    const response = await axios.get(SIGN_UP_PHONE_CHECK_URL(), {
      params: { phone: param },
    });
    const responseBody: PhoneValdateResponseAtSignUpDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 휴대폰 인증 요청 o
export const phoneValidateRequest = async (param: string) => {
  try {
    const response = await axios.get(PHONE_CHECK_URL(), {
      params: { phone: param },
    });
    const responseBody: PhoneValidateResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 회원 가입 요청 o
export const signUpRequest = async (requestBody: SignUpRequestDto) => {
  try {
    const response = await axios.post(SIGN_UP_URL(), requestBody);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 소셜 로그인 - 인가 코드 요청 o
export const kakaoCallbackAuthCodeRequest = async (authCode: string | null) => {
  try {
    const response = await axios.post(KAKAO_CALLBACK_URL(), authCode);
    const resonseBody: SignInResponseDto = response.data;
    return resonseBody;

    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 토큰 재발급 요청 o
export const refreshTokenRequest = async () => {
  try {
    const response = await axios.get(REFRESH_TOKEN_URL());
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 로그아웃 요청 o
export const logoutRequest = async () => {
  try {
    const response = await axios.get(LOGOUT_URL());
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 소셜 로그인 - 로그아웃 요청 o
export const kakaoLogoutRequest = async () => {
  try {
    const response = await axios.get(KAKAO_LOGOUT_URL());
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 마이페이지 - 회원정보 요청 o
export const mypageInfoRequest = async (uuid: string | null) => {
  try {
    const response = await axios.get(MYPAGE_URL(uuid));
    const responseBody: MypageResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 마이페이지 - 회원 정보 수정 요청 o
export const myInfoUpdateRequest = async (
  uuid: string | null,
  requestDto: MypageUpdateRequestDto,
) => {
  try {
    const response = await axios.put(MY_INFO_UPDATE_URL(uuid), requestDto);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 마이페이지 - 나의 판매 내역 요청
export const myDealListRequest = async (uuid: string | null, page: number) => {
  try {
    const response = await axios.get(MY_DEAL_LIST_URL(), {
      params: { uuid: uuid, page: page },
    });
    const responseBody: MyDealListWithPageResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 마이페이지 - 판매글 수정 요청 o
export const saleStatusChangeRequest = async (requestDto: DealInfoStatusUpdateRequestDto) => {
  try {
    const response = await axios.put(SALESTATUS_CHANGE_URL(), requestDto);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 마이페이지 - 판매글 삭제 요청 o
export const deletePostListRequest = async (idArr: number[]) => {
  try {
    const response = await axios.put(DELETE_POSTLIST_URL(), idArr);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 비밀번호 확인 요청 o
export const pwCheckRequest = async (requestDto: PwCheckRequestDto) => {
  try {
    const response = await axios.post(PW_CHECK_URL(), requestDto);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 회원 탈퇴 요청 o
export const deleteUserRequest = async (uuid: string | null) => {
  try {
    const response = await axios.delete(DELETE_URL(uuid));
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 소셜 로그인 - 회원 탈퇴 요청 △ 기능 수정(의도한대로 동작 X)
export const deleteKakaoUserRequest = async (kakaoId: string, uuid: string | null) => {
  try {
    const response = await axios.delete(KAKAO_DELETE_URL(), {
      params: {
        kakaoId: kakaoId,
        uuid: uuid,
      },
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

// api request : 채팅방 목록 요청
export const loadChatRoomListRequest = async (uuid: string | null) => {
  try {
    const response = await axios.get(CHAT_ROOM_LIST(), {
      params: {
        consumer: uuid,
      },
    });
    const responseBody: ChatRoomListResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    console.error('채팅 내역 로드 실패', error);
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// comment: *중요 - 순환참조로 인해 Interceptor 내부에서 axios 요청 금지
axios.interceptors.response.use(
  (res) => res,
  async (err) => {
    const {
      config,
      response: { status },
    } = err;

    if (status === 403) {
      console.log('403 Forbidden');
      sessionStorage.clear();
      window.location.href = `http://localhost:3000/auth`; // 로그인 페이지로 이동
      return;
    }

    if (status === 504) {
      console.log('504 Gateway Timeout Error');
      alert('😒 네트워크 연결 상태를 확인해주세요!');
      return;
    }

    if (status === 503) {
      console.log('503 Service Unavailable');
      alert('💀 서버가 켜져있지 않습니다! 확인바랍니다!');
      return;
    }

    if (config.url === REFRESH_TOKEN_URL || status !== 401 || config.sent) {
      return Promise.reject(err);
    }

    config.sent = true;
    await refreshTokenRequest();
    return axios(config); // 재요청
  },
);
