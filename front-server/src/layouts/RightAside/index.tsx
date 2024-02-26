import SideButtonBox from 'components/SideButtonBox';
import { useEffect, useState } from 'react';
import './style.css';

// component: 사이드버튼 컴포넌트//
export default function RightAside() {
  // state: 로그인 상태 //
  const [role, setRole] = useState<string | null>(null);
  // state: 회원 등급 상태 //
  const [isUser, setUser] = useState<boolean>(false);
  // state: 관리자 등급 상태 //
  const [isAdmin, setAdmin] = useState<boolean>(false);

  // effect: 페이지 리랜더링 시마다 수행 //
  useEffect(() => {
    const r = sessionStorage.getItem('role');
    setRole(r);
  }, [sessionStorage.getItem('role')]);

  // effect: role 값의 변경을 감지시마다 수행 //
  useEffect(() => {
    if (role === 'USER') {
      setUser(true);
      return;
    } else if (role === 'ADMIN') {
      setAdmin(true);
      return;
    } else {
      setUser(false);
      setAdmin(false);
      return;
    }
  }, [role]);

  // render: 사이드버튼 컴포넌트 렌더링//
  return (
    <div className="right-aside-wrapper">
      <SideButtonBox isUser={isUser} isAdmin={isAdmin} uuid={''} />
    </div>
  );
}
