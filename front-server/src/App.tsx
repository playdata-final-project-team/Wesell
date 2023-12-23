import { Routes, Route } from 'react-router-dom';
import './App.css';
import Main from 'pages/Main';
import Container from 'layouts/Container';
import {
  MAIN_PATH,
  SOCIAL_PATH,
  UPLOAD_PATH,
  BOARD_DETAIL,
  MYPAGE_PATH,
  BOARD_EDIT,
  AUTH_PATH,
} from 'constant';
import Social from 'pages/Social/kakao';
import UploadBoard from 'pages/board/write/Write';
import PostDetailPage from 'pages/board/detail';
import Mypage from 'pages/Mypage';
import EditPostPage from 'pages/board/edit';
import UuidComponent from 'pages/Userfindbyid/UuidComponent';
import FoundEmailComponent from 'pages/Userfindbyid/FoundEmailComponent';
import EmailfindComponent from 'pages/UserfindbyPwd/EmailfindComponent';
import FoundSmsComponent from 'pages/UserfindbyPwd/FoundSmsComponent';
import PasswordUpdateComponent from 'pages/UserfindbyPwd/PasswordUpdateComponent';
import AuthServer from 'pages/Authentication';
import { useEffect, useState } from 'react';
import Test from 'pages/Test';
import { ToastContainer } from 'react-toastify';

// component: Application 컴포넌트 //
function App() {
  // state: 로그인 여부 확인 상태값 //
  const [isLogin, setLogin] = useState<boolean>(false);

  // effect: 페이지 렌더링 시마다 로그인 여부 확인 //
  useEffect(() => {
    const uuid = sessionStorage.getItem('uuid');
    const role = sessionStorage.getItem('role');

    if (uuid && role) {
      setLogin(true);
    } else {
      setLogin(false);
    }
  }, []);

  // render: Application 컴포넌트 랜더링 //
  return (
    <>
      <ToastContainer />
      <Routes>
        <Route element={<Container />}>
          {/** 테스트 URL - S*/}
          <Route path="/test" element={<Test />}></Route>

          {/** 테스트 URL - E*/}

          <Route path={MAIN_PATH()} element={<Main />} />
          <Route path={SOCIAL_PATH('kakao')} element={<Social />} />
          <Route path={AUTH_PATH()} element={<AuthServer />} />
          <Route path="testJiho2" element={<UuidComponent />} />
          <Route path="/found-email/:uuid" element={<FoundEmailComponent />} />
          <Route path="testJiho4" element={<EmailfindComponent />} />
          <Route path="testJiho5/:uuid" element={<FoundSmsComponent />} />
          <Route path="/testJiho6/:uuid" element={<PasswordUpdateComponent />} />
          <Route path="*" element={<h1>404 NOT FOUND</h1>} />
          {isLogin ? (
            <>
              <Route path={UPLOAD_PATH()} element={<UploadBoard />} />
              <Route path={BOARD_DETAIL()} element={<PostDetailPage />} />
              <Route path={MYPAGE_PATH()} element={<Mypage />} />
              <Route path={BOARD_EDIT()} element={<EditPostPage />} />
            </>
          ) : null}
        </Route>
      </Routes>
    </>
  );
}

export default App;
