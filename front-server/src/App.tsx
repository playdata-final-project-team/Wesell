import { Routes, Route } from 'react-router-dom';
import './App.css';
import Main from 'pages/Main';
import Container from 'layouts/Container';
import {
  MAIN_PATH,
  SOCIAL_PATH,
  TEST_PATH,
  UPLOAD_PATH,
  BOARD_DETAIL,
  MYPAGE_PATH,
  BOARD_EDIT,
  AUTH_PATH,
} from 'constant';
import CorsTest from 'pages/Test';
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

// component: Application 컴포넌트 //
function App() {
  // render: Application 컴포넌트 랜더링 //
  return (
    <Routes>
      <Route element={<Container />}>
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path={SOCIAL_PATH('kakao')} element={<Social />} />
        <Route path={AUTH_PATH()} element={<AuthServer />} />
        <Route path={TEST_PATH()} element={<CorsTest />} />
        <Route path={TEST_PATH()} element={<></>} />
        <Route path={UPLOAD_PATH()} element={<UploadBoard />} />
        <Route path={BOARD_DETAIL()} element={<PostDetailPage />} />
        <Route path={MYPAGE_PATH()} element={<Mypage />} />
        <Route path={BOARD_EDIT()} element={<EditPostPage />} />
        <Route path="*" element={<h1>404 NOT FOUND</h1>} />
        <Route path="testJiho2" element={<UuidComponent />} />
        <Route path="/found-email/:uuid" element={<FoundEmailComponent />} />
        <Route path="testJiho4" element={<EmailfindComponent />} />
        <Route path="testJiho5/:uuid" element={<FoundSmsComponent />} />
        <Route path="/testJiho6/:uuid" element={<PasswordUpdateComponent />} />
      </Route>
    </Routes>
  );
}

export default App;
