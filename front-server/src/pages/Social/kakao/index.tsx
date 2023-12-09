import { useEffect } from 'react';
import { kakaoCallbackAuthCodeRequest } from 'apis';
import Loading from 'components/Loading';
import { useLocation, useNavigate } from 'react-router-dom';
import { MAIN_PATH } from 'constant';

const Social: React.FC = () => {
  const location = useLocation();
  const queryParams = new URLSearchParams(location.search);
  const authCode = queryParams.get('code');
  const error = queryParams.get('error');

  // function: 네비게이터 함수 //
  const navigator = useNavigate();

  // alert(`인가 코드 정상 발급! : [ ${authCode} ]`);
  useEffect(() => {
    if(authCode){kakaoCallbackAuthCodeRequest(authCode);} 
    if(error){alert('카카오 로그인 실패하셨습니다.'); return;}
    alert('로그인에 성공하셨습니다.');
    navigator(MAIN_PATH());
  }, []);

  return <Loading />
};

export default Social;
