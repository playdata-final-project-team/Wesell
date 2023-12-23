import './style.css';
import { ChangeEvent, Dispatch, SetStateAction, useState } from 'react';
import InputBox from 'components/InputBox';
import { PwCheckRequestDto } from 'apis/request/delete';
import useStore from 'stores';
import { deleteUserRequest, pwCheckRequest } from 'apis';
import { ResponseDto } from 'apis/response';
import ResponseCode from 'constant/response-code.enum';
import { useNavigate } from 'react-router-dom';

// interface: 회원탈퇴 삭제 box 컴포넌트 Props //
interface Props {
  setPopupOpen: Dispatch<SetStateAction<boolean>>;
}

// component: 회원 탈퇴 컴포넌트 //
const Withdraw = (props: Props) => {
  const { setPopupOpen } = props;

  const uuid = useStore((state) => state.uuid);

  // state: 회원 탈퇴 card 상태값 //
  const [view, setView] = useState<'pw-check' | 'confirm-msg'>('pw-check');

  // state: 비밀번호 상태값 //
  const [pw, setPw] = useState<string>('');
  const [pwError, setPwError] = useState<boolean>(false);
  const [pwErrorMsg, setPwErrorMsg] = useState<string>('');

  const navigator = useNavigate();

  // event-handler: 비밀번호 chagne event handler //
  const onPwChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
    setPwError(false);
    setPwErrorMsg('');
    const { value } = event.target;
    setPw(value);
  };

  // function: 비밀번호 제출 요청에 대한 응답 처리//
  function pwCheckResponse(responseBody: ResponseDto | null) {
    if (!responseBody) {
      alert('네트워크 연결 상태를 확인해주세요!');
      return;
    }

    const { code } = responseBody;

    if (code === ResponseCode.NOT_FOUND_USER || code === ResponseCode.NOT_CORRECT_PASSWORD) {
      alert('비밀번호가 틀립니다. 다시 입력 바랍니다.');
      return;
    }

    setView('confirm-msg');
  }

  // event-handler: 비밀번호 제출 button click event handler //
  const onPwBtnClickHandler = async () => {
    const requestDto: PwCheckRequestDto = {
      uuid,
      pw,
    };

    if (!requestDto.uuid) {
      // alert("로그인 바랍니다.");
      // navigator("/");
      // return;
    }

    if (requestDto.pw === '') {
      setPwError(true);
      setPwErrorMsg('비밀번호를 입력 바랍니다.');
      return;
    }

    await pwCheckRequest(requestDto).then(pwCheckResponse);
  };

  // event-handler: 닫기 버튼 click event handler//
  const onCloseBtnClickHandler = () => {
    setPopupOpen(false);
  };

  // function: 회원 탈퇴 요청에 대한 응답 처리 //
  function deleteUserResponse(responseBody: ResponseDto | null) {
    if (!responseBody) {
      alert('네트워크 연결 상태를 확인해주세요!');
      return;
    }

    // session storage 삭제
    window.sessionStorage.clear();

    alert('회원 탈퇴가 완료되었습니다.');

    navigator('/');
  }

  // event-handler: 삭제 확인 버튼 YES click event handler //
  const onYesClickHandler = async () => {
    await deleteUserRequest(uuid).then(deleteUserResponse);
  };

  // event-handler: 삭제 확인 버튼 No click event handler //
  const onNoClickHandler = () => {
    navigator('/mypage');
  };

  // render: 회원 탈퇴 컴포넌트 렌더링//
  return (
    <div className="withdraw-wrapper">
      <div className="withdraw-container">
        {view === 'pw-check' && (
          <div className="withdraw-content-pwCheck">
            <h2>비밀번호 확인</h2>
            <InputBox
              name="pwRe"
              placeholder="password"
              type="password"
              value={pw}
              error={pwError}
              message={pwErrorMsg}
              onChange={onPwChangeHandler}
            />
            <button className="pw-check-request-btn" type="button" onClick={onPwBtnClickHandler}>
              입력
            </button>
          </div>
        )}
        {view === 'confirm-msg' && (
          <div className="withdraw-content-confirmMsg">
            <p>정말 삭제하시겠습니까?</p>
            <div onClick={onYesClickHandler}>YES</div>
            <div onClick={onNoClickHandler}>NO</div>
          </div>
        )}
        <button className="close-btn" title="close" type="button" onClick={onCloseBtnClickHandler}>
          닫기
        </button>
      </div>
    </div>
  );
};

export default Withdraw;
