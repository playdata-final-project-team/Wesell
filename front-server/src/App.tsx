import { Routes, Route } from 'react-router-dom';
import './App.css';
import AuthServer from 'pages/Authentication';
import Main from 'pages/Main';
import Container from 'layouts/Comtainer';
import { MAIN_PATH, TEST_PATH } from 'constant';
import { AUTH_PATH } from 'constant';
import CorsTest from 'pages/Test';

// component: Application 컴포넌트 //
function App() {
  // render: Application 컴포넌트 랜더링 //
  // comment: 메인 화면 : '/' - Main //
  // comment: 로그인 + 회원가입 : '/auth' - Authentication //
  // comment: 검색 화면 : '/search/:word' - Search //
  // comment: 회원 페이지: '/user-service/mypage - User //
  // comment: 게시물 상세보기: '/board/detail/:boardNumber' - BoardDetail //
  // comment: 게시물 상세보기: '/board/write' - BoardWrite //
  // comment: 게시물 수정하기: '/board/update/:boardNumber' - BoardUpdate //

  return (
    <Routes>
      <Route element={<Container />}>
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path={AUTH_PATH()} element={<AuthServer />} />
        <Route path={TEST_PATH()} element={<CorsTest />} />
        <Route path="*" element={<h1>404 NOT FOUND</h1>} />
      </Route>
    </Routes>
  );
}

export default App;
