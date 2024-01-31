import { kakaoCallbackAuthCodeRequest } from 'apis';
import { ResponseDto } from 'apis/response';
import { SignInResponseDto } from 'apis/response/auth';
import Loading from 'components/Loading';
import { MAIN_PATH } from 'constant';
import ResponseCode from 'constant/response-code.enum';
import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

export default function Kakao() {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const authCode = queryParams.get('code');
  const error = queryParams.get('error');

  /**d
   * d
   */
  // function: 네비게이터 함수 //
  const navigator = useNavigate();

  // function: 카카오 소셜 로그인 응답 처리 함수 //
  const kakaoCallbackResponse = (responseBody: ResponseDto | SignInResponseDto | null) => {
    if (!responseBody) {
      return;
    }

    const { code } = responseBody;

    console.log(`카카오 로그인 ${code}`);

    if (code === ResponseCode.USER_SERVICE_FEIGN_ERROR) {
      console.error('유저 서버 페인 요청 시 오류 발생');
      alert('😒서버 오류로 인해 서비스 이용이 불가합니다. 잠시 후 이용해주세요');
      return;
    }

    const responseBodyWithUserInfo = responseBody as SignInResponseDto;
    console.log(responseBodyWithUserInfo);
    if (responseBodyWithUserInfo.content) {
      window.sessionStorage.setItem('uuid', responseBodyWithUserInfo.content.uuid);

      window.sessionStorage.setItem('role', responseBodyWithUserInfo.content.role);

      window.sessionStorage.setItem('kakaoId', responseBodyWithUserInfo.content.kakaoId.toString());
    }

    navigator(MAIN_PATH());
  };

  // effect: 소셜 로그인 요청 처리 effect//
  useEffect(() => {
    if (authCode) {
      kakaoCallbackAuthCodeRequest(authCode).then(kakaoCallbackResponse);
    } else if (error) {
      console.error('카카오로 인가 코드 요청 시 오류 발생.', error);
      alert('😒카카오 로그인 실패하셨습니다.');
      return;
    }
  }, []);

  return <Loading />;
}
