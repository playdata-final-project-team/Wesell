import './style.css';
import ButtonBox from 'components/ButtonBox';
import { logoutRequest } from 'apis';
import ResponseCode from 'constant/response-code.enum';
import { useState } from 'react';
import  useStore  from 'stores';

// component: 헤더 레이아웃 //
export default function Header() {

  // context: role //
  const [role, setRole] = useState<string | null>(useStore((state) => state.role));

  // event-handler: 로그아웃 버튼 click 이벤트 핸들러 //
  const onLogoutBtnClickHandler = async () => {
    const responseBody = await logoutRequest();

    if (!responseBody) {
      alert('네트워크 연결 상태를 확인해주세요!');
      return;
    }

    const { code } = responseBody;

    if(code === ResponseCode.OK){
      window.sessionStorage.clear();
      setRole(null);
      console.log("로그아웃!");
    }
  }

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
          {role && <ButtonBox label='로그아웃' isEnable={true} type='button' 
          onClick={onLogoutBtnClickHandler}/>}
        </div>
      </div>
    </div>
  );
}
