import { Outlet, useLocation } from 'react-router-dom';
import Header from 'layouts/Header';
import RightAside from 'layouts/RightAside';
import LeftAside from 'layouts/LeftAside';
import './style.css';

// component: 레이아웃 //
export default function Container() {
  // state: 현재 페이지 path name 상태 //
  const { pathname } = useLocation();

  // render: 레이아웃 렌더링 //
  return (
    <>
      <Header />
      <div id="main">
        {pathname === '/' && <LeftAside />}
        <Outlet />
        {(pathname === '/' || pathname.includes('/category') || pathname.includes('/title')) && (
          <RightAside />
        )}
      </div>
    </>
  );
}
