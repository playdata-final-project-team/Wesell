import ImageBUttonItem from 'components/SideIconButtonItem';
import './style.css';
import { useNavigate } from 'react-router-dom';
import { AUTH_PATH, MYPAGE_PATH } from 'constant';
import { CgAddR } from 'react-icons/cg';

// interface: Props //
interface Props {
  isUser: boolean;
  isAdmin: boolean;
  uuid: string;
}

// component: side buton box 컴포넌트 //
export default function SideButtonBox(props: Props) {
  const { isUser, isAdmin, uuid } = props;

  const navigator = useNavigate();

  // event-handler: 로그인 이미지 버튼 클릭 이벤트 처리 //
  const onLoginBtnClickHandler = () => {
    navigator(AUTH_PATH());
    return;
  };

  // event-handler: 마이페이지 이미지 버튼 클릭 이벤트 처리 //
  const onMypageBtnClickHandler = () => {
    navigator(MYPAGE_PATH());
    return;
  };

  // event-handler: 관리자페이지 이미지 버튼 클릭 이벤트 처리 //
  const onAdminPageBtnClickHandler = () => {
    return <p>관리자 페이지</p>; // 관리자 페이지 이동 경로 추가
  };

  // event-handler: 게시글 작성 이미지 버튼 클릭 이벤트 처리 //
  const onWritePostBtnClickHandler = () => {
    return <></>;
  };

  // render: side button box 렌더링 //
  return (
    <div className="side-button-container">
      <div className="side-button">
        {!isUser && !isAdmin ? (
          <ImageBUttonItem
            tooltip="로그인"
            icon="basic-profile"
            alt="로그인"
            onIconClick={onLoginBtnClickHandler}
          />
        ) : null}
        {isUser && (
          <ImageBUttonItem
            tooltip="마이페이지"
            icon="basic-profile"
            alt="마이 페이지"
            onIconClick={onMypageBtnClickHandler}
          />
        )}
        {isAdmin && (
          <ImageBUttonItem
            tooltip="관리자페이지"
            icon="admin-profile"
            alt="관리자 페이지"
            onIconClick={onAdminPageBtnClickHandler}
          />
        )}
      </div>
      <div className="side-button">
        {isUser && (
          <CgAddR className="side-icon-btn">
            <div title="게시글 작성" onClick={onWritePostBtnClickHandler}></div>
          </CgAddR>
        )}
      </div>
    </div>
  );
}
