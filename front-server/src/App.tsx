import { Route, Routes } from 'react-router-dom';
import './App.css';
import Main from 'pages/Main';
import Container from 'layouts/Container';
import { MAIN_PATH, SOCIAL_PATH, UPLOAD_PATH, MYPAGE_PATH, AUTH_PATH } from 'constant';
import UploadBoard from 'pages/board/write/Write';
import Mypage from 'pages/Mypage';
import EditPostPage from 'pages/board/edit';
import UuidComponent from 'pages/Userfindbyid/UuidComponent';
import FoundEmailComponent from 'pages/Userfindbyid/FoundEmailComponent';
import EmailfindComponent from 'pages/UserfindbyPwd/EmailfindComponent';
import FoundSmsComponent from 'pages/UserfindbyPwd/FoundSmsComponent';
import PasswordUpdateComponent from 'pages/UserfindbyPwd/PasswordUpdateComponent';
import SearchByCategory from 'pages/search/category';
import SearchByTitle from 'pages/search/title';
import AuthServer from 'pages/Authentication';
import PostDetailPage from 'pages/board/detail';
import AdminUsersComponent from 'pages/AdminUsers/AdminUsersComponent';
import ChatRoomPage from 'pages/ChatRoomPage';
import RequestPay from 'pages/Pay/beforePay';
import PayResultPage from 'pages/Pay/detail';
import TestDetailPage from 'pages/board/testDetail';
import Kakao from 'pages/Social/kakao';

// component: Application 컴포넌트 //
function App() {
  // render: Application 컴포넌트 랜더링 //
  return (
    <Routes>
      <Route element={<Container />}>
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path="/category/:categoryId" element={<SearchByCategory />} />
        <Route path="/title/:title" element={<SearchByTitle />} />
        <Route path={SOCIAL_PATH('kakao')} element={<Kakao />} />
        <Route path={AUTH_PATH()} element={<AuthServer />} />
        <Route path="/found-email/:uuid" element={<FoundEmailComponent />} />
        <Route path="/find-id" element={<UuidComponent />} />
        <Route path="/find-pw" element={<EmailfindComponent />} />
        <Route path="/phone/valid/:uuid" element={<FoundSmsComponent />} />
        <Route path="/update-pw/:uuid" element={<PasswordUpdateComponent />} />
        <Route path="/product/detail/:postId" element={<PostDetailPage />} />
        <Route path={UPLOAD_PATH()} element={<UploadBoard />} />
        <Route path={MYPAGE_PATH()} element={<Mypage />} />
        <Route path="/product/update/:postId" element={<EditPostPage />} />
        <Route path="/admin/users" element={<AdminUsersComponent />} />
        <Route path="*" element={<h1>404 NOT FOUND</h1>} />
        <Route path="/rooms" element={<ChatRoomPage />} />
        <Route path="/rooms/:roomId" element={<ChatRoomPage />} />
        <Route path="/payment/:postId" element={<RequestPay />} />
        <Route path="payment/:payId" element={<PayResultPage />} />
      </Route>

      {/* 테스트 라우터 */}
      <Route>
        <Route path="/test/detail/:productId" element={<TestDetailPage />} />
      </Route>
    </Routes>
  );
}
export default App;
