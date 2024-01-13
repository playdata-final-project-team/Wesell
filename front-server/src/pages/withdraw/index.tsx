import './style.css';
import { ChangeEvent, Dispatch, SetStateAction, useEffect, useState } from 'react';
import InputBox from 'components/InputBox';
import { PwCheckRequestDto } from 'apis/request/delete';
import useStore from 'stores';
import { deleteKakaoUserRequest, deleteUserRequest, pwCheckRequest } from 'apis';
import { ResponseDto } from 'apis/response';
import ResponseCode from 'constant/response-code.enum';
import { useNavigate } from 'react-router-dom';
import { MAIN_PATH, MYPAGE_PATH } from 'constant';

// interface: 회원탈퇴 삭제 box 컴포넌트 Props //
interface Props {
  setPopupOpen: Dispatch<SetStateAction<boolean>>;
}

// component: 회원 탈퇴 컴포넌트 //
const Withdraw = (props: Props) => {
  const { setPopupOpen } = props;

  const uuid = sessionStorage.getItem('uuid');
  const kakaoId = sessionStorage.getItem('kakaoId');

  // state: 회원 탈퇴 card 상태값 //
  const [view, setView] = useState<'pw-check' | 'comment-check' | 'confirm-msg'>('pw-check');

  // state: 비밀번호 상태값 //
  const [pw, setPw] = useState<string>('');
  const [pwError, setPwError] = useState<boolean>(false);
  const [pwErrorMsg, setPwErrorMsg] = useState<string>('');

  // state: 멘트 상태값 //
  const [comment, setComment] = useState<string>('');
  const [commentError, setCommentError] = useState<boolean>(false);
  const [commentErrorMsg, setCommentErrorMsg] = useState<string>('');

  const navigator = useNavigate();

  useEffect(() => {
    if (kakaoId) {
      setView('comment-check');
      return;
    } else {
      return;
    }
  }, []);

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
      alert('로그인 바랍니다.');
      navigator('/');
      return;
    }

    if (requestDto.pw === '') {
      setPwError(true);
      setPwErrorMsg('비밀번호를 입력 바랍니다.');
      return;
    }

    await pwCheckRequest(requestDto).then(pwCheckResponse);
  };

  // event-handler 멘트 제출 button click event handler //
  const onCommentBtnClickHandler = () => {
    if (comment === '계정삭제') {
      setView('confirm-msg');
    } else {
      setCommentError(true);
      setCommentErrorMsg('"계정삭제" 라고 입력해주세요');
      setComment('');
    }
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

    navigator(MAIN_PATH());
  }

  // event-handler: 삭제 확인 버튼 YES click event handler //
  const onYesClickHandler = async () => {
    const isKakaoSignin = sessionStorage.getItem('kakaoId');
    const uuid = sessionStorage.getItem('uuid');
    if (isKakaoSignin) {
      await deleteKakaoUserRequest(isKakaoSignin, uuid).then(deleteUserResponse);
    } else {
      await deleteUserRequest(uuid).then(deleteUserResponse);
    }
  };

  // event-handler: 삭제 확인 버튼 No click event handler //
  const onNoClickHandler = () => {
    navigator(MYPAGE_PATH());
  };

  // render: 회원 탈퇴 컴포넌트 렌더링//
  return (
    <div className="withdraw-wrapper">
      <div className="withdraw-container">
        <button
          className="close-btn"
          title="close"
          type="button"
          onClick={onCloseBtnClickHandler}
        />
        {view === 'pw-check' && (
          <div className="pw-check-card">
            <h2>비밀번호 확인</h2>
            <div className="withdraw-content-pwCheck">
              <InputBox
                name="pwRe"
                placeholder="password"
                type="password"
                value={pw}
                error={pwError}
                message={pwErrorMsg}
                onChange={onPwChangeHandler}
                btnVlaue={'입력'}
                onBtnClick={onPwBtnClickHandler}
              />
            </div>
          </div>
        )}
        {view === 'comment-check' && (
          <div className="comment-check-card">
            <h2>삭제 확인</h2>
            <InputBox
              name="comment"
              placeholder="계정삭제"
              type="text"
              value={comment}
              error={commentError}
              message={commentErrorMsg}
              onChange={(e) => {
                setComment(e.target.value);
                setCommentError(false);
                setCommentErrorMsg('');
              }}
              btnVlaue={'입력'}
              onBtnClick={onCommentBtnClickHandler}
            />
          </div>
        )}
        {view === 'confirm-msg' && (
          <div className="withdraw-content-confirmMsg">
            <p>정말 삭제하시겠습니까?</p>
            <div className="cinfirmMsg-element">
              <div onClick={onYesClickHandler}>YES</div>
              <div onClick={onNoClickHandler}>NO</div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Withdraw;
