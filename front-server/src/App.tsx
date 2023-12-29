import { Routes, Route, Navigate } from 'react-router-dom';
import './App.css';
import Main from 'pages/Main';
import Container from 'layouts/Container';
import { MAIN_PATH, SOCIAL_PATH, UPLOAD_PATH, MYPAGE_PATH, AUTH_PATH } from 'constant';
import Social from 'pages/Social/kakao';
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
import { useEffect, useState } from 'react';
import Test from 'pages/Test';
import PostDetailPage from 'pages/board/detail';
import AdminUsersComponent from 'pages/AdminUsers/AdminUsersComponent';

// component: Application 컴포넌트 //
function App() {
  // state: 로그인 여부 확인 상태값 //
  const [isLogin, setLogin] = useState<boolean>(false);

  // state: 관리자 여부 확인 상태값 //

  const [isAdmin, setAdmin] = useState<boolean>(false);

  // effect: 페이지 렌더링 시마다 로그인 여부 확인 //
  useEffect(() => {
    const uuid = sessionStorage.getItem('uuid');
    const role = sessionStorage.getItem('role');
    console.log(uuid);
    if (uuid && role) {
      setLogin(true);
      if (uuid === 'ADMIN') {
        setAdmin(true);
      } else {
        setAdmin(false);
      }
    } else {
      setLogin(false);
    }
  }, []);

  // render: Application 컴포넌트 랜더링 //
  return (
    <Routes>
      <Route element={<Container />}>
        {/** 테스트 URL - S*/}
        <Route path="/test" element={<Test />}></Route>
        {/** 테스트 URL - E*/}
        <Route path={MAIN_PATH()} element={<Main />} />
        <Route path="/category/:categoryId" element={<SearchByCategory />} />
        <Route path="/title/:title" element={<SearchByTitle />} />
        <Route path={SOCIAL_PATH('kakao')} element={<Social />} />
        <Route path={AUTH_PATH()} element={<AuthServer />} />
        <Route path="/found-email/:uuid" element={<FoundEmailComponent />} />
        <Route path="/find-id" element={<UuidComponent />} />
        <Route path="/find-pw" element={<EmailfindComponent />} />
        <Route path="/phone/valid/:uuid" element={<FoundSmsComponent />} />
        <Route path="/update-pw/:uuid" element={<PasswordUpdateComponent />} />
        <Route path="/board/detail/:postId" element={<PostDetailPage />} />
        <Route path="*" element={<h1>404 NOT FOUND</h1>} />
        <Route path="/board/edit/:postId" element={<EditPostPage />} />
        {isLogin ? (
          <>
            <Route path={UPLOAD_PATH()} element={<UploadBoard />} />
            <Route path={MYPAGE_PATH()} element={<Mypage />} />
            {/* <Route path="/board/edit/:postId" element={<EditPostPage />} /> */}
          </>
        ) : null}
        {isAdmin ? (
          <>
            <Route path="/admin/users" element={<AdminUsersComponent />} />
          </>
        ) : null}
      </Route>
    </Routes>
  );
}
export default App;
