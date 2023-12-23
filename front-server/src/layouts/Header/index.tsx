import './style.css';
import ButtonBox from 'components/ButtonBox';
import { logoutRequest } from 'apis';
import ResponseCode from 'constant/response-code.enum';
import { useEffect, useState } from 'react';

// component: 헤더 레이아웃 //
export default function Header() {
  // state: 로그인 상태 //
  const [role, setRole] = useState<string | null>(null);
  // state: 회원 등급 상태 //
  const [isUser, setUser] = useState<boolean>(false);
  // state: 관리자 등급 상태 //
  const [isAdmin, setAdmin] = useState<boolean>(false);

  // effect: 페이지 리랜더링 시마다 수행 //
  useEffect(() => {
    const r = sessionStorage.getItem('role');
    if (r) {
      setRole(r);
    }
  }, []);

  // effect: role 값의 변경을 감지시마다 수행 //
  useEffect(() => {
    if (role === 'USER') {
      setUser(true);
    } else if (role === 'ADMIN') {
      setAdmin(true);
    } else {
      setUser(false);
      setAdmin(false);
    }
  }, [role]);

  // event-handler: 로그아웃 버튼 click 이벤트 핸들러 //
  const onLogoutBtnClickHandler = async () => {
    const responseBody = await logoutRequest();

    if (!responseBody) {
      alert('네트워크 연결 상태를 확인해주세요!');
      return;
    }

    const { code } = responseBody;

    if (code === ResponseCode.OK) {
      window.sessionStorage.clear();
      console.log('로그아웃!');
    }
  };

  // component: 로그인 또는 마이페이지 버튼 컴포넌트 //

  // render: 헤더 레이아웃 컴포넌트 렌더링 //
  return (
    <div id="header">
      <div className="header-container">
        <div className="header-left-box">
          <div className="icon-box">
            <a href="/">
              <div className="icon logo-wesell-icon"></div>
            </a>
          </div>
        </div>
        <div className="header-right-box">
          {(isUser || isAdmin) && (
            <ButtonBox
              label="로그아웃"
              isEnable={true}
              type="button"
              onClick={onLogoutBtnClickHandler}
            />
          )}
        </div>
      </div>
    </div>
  );
}
