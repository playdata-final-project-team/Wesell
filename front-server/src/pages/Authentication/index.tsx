import InputBox from 'components/InputBox';
import { useState, useRef, KeyboardEvent, ChangeEvent } from 'react';
import './style.css';
import CheckBox from 'components/CheckBox';
import { SignInRequestDto } from 'apis/request/auth';
import { nicknameDupCheckRequest, signInRequest } from 'apis';
import { SignInResponseDto } from 'apis/response/auth';
import { ResponseDto } from 'apis/response';
import ButtonBox from 'components/ButtonBox';

// component: 인증 화면 컴포넌트 //
function AuthServer() {

  // state: 페이지 상태 //
  const [view, setView] = useState<'sign-in' | 'sign-up'>('sign-in');

  // component: 로그인 컴포넌트 //
  const SignInCard = () => {
    // state: 이메일 요소 참조 상태 //
    const emailRef = useRef<HTMLInputElement | null>(null);

    // state: 비밀번호 요소 참조 상태 //
    const passwordRef = useRef<HTMLInputElement | null>(null);

    // state: 이메일 value 상태 //
    const [email, setEmail] = useState<string>('');

    // state: 비밀번호 value 상태 //
    const [password, setPassword] = useState<string>('');

    // state: 비밀번호 type 상태 //
    const [passwordType, setPasswordType] = useState<'text' | 'password'>('password');

    // state: 비밀번호 표시/숨김 아이콘 상태 //
    const [passwordIcon, setPasswordIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>(
      'eye-light-off-icon',
    );

    // state: 체크박스 value 상태 //
    const [savedId, setSavedId] = useState<boolean>(false);

    // state: 에러 상태 //
    const [error, setError] = useState<boolean>(false);

    // function: sign in response 처리 함수//
    const signInResponse = (responseBody: SignInResponseDto | ResponseDto | null) => {
      if (!responseBody) {
        alert('네트워크 상태를 확인해주세요.'); // comment: 서버가 안켜진 경우 또는 도메인 주소가 잘못된 경우 //
        return;
      }

      const { code } = responseBody;
      if (code === 'TSE') alert('서바 내 오류 입니다.');
      if (code === 'SIF' || code === 'VF') setError(true);
      if (code !== 'NFU') return;
    };

    // event-handler: 이메일 변경 이벤트 처리 //
    const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setEmail(value);
    };

    // event-handler: 비밀번호 변경 이벤트 처리 //
    const onPasswordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setPassword(value);
    };

    // event-handler: 로그인 버튼 click 이벤트 처리 //
    const onSignInButtonClickHandler = () => {
      const requestBody: SignInRequestDto = { email, password, savedId };
      signInRequest(requestBody).then(signInResponse);
    };

    // event-handler: 소셜 로그인 button click 이벤트 처리//
    const onSocialLoginClickHandler = () => {

    // comment: kakao 인가번호 요청용 리소스 //
      const CLIENT_ID = process.env.REACT_APP_CLIENT_ID;
      const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
      window.location.href = `https://kauth.kakao.com/oauth/authorize?client_id=${CLIENT_ID}&redirect_uri=${REDIRECT_URI}&response_type=code`;
    };

    // event-handler: 비밀번호 표시/숨김 아이콘 버튼 click 이벤트 처리 //
    const onPasswordIconClickHandler = () => {
      if (passwordType === 'text') {
        setPasswordType('password');
        setPasswordIcon('eye-light-off-icon');
      } else {
        setPasswordType('text');
        setPasswordIcon('eye-light-on-icon');
      }
    };

    // event-handler: 회원가입 링크 click 이벤트 처리 //
    const onSignUpClickHandler = () => {
      setView('sign-up');
    };

    // event-handler: 이메일 input key-down 이벤트 처리//
    const onEmailKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!passwordRef.current) return;
      passwordRef.current.focus();
    };

    // event-handler: 비밀번호 input key-down 이벤트 처리 //
    const onPasswordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      onSignInButtonClickHandler();
    };

    // event-handler: 아이디 저장 change 이벤트 처리 //
    const onCheckBoxChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const isChecked = event.target.checked;
      setSavedId(isChecked);
    };

    // render: 로그인 컴포넌트 랜더링 //
    return (
      <div className="auth-card">
        <div className="auth-card-box">
          <div className="auth-card-top">
            <InputBox
              ref={emailRef}
              name="email"
              placeholder="e-mail"
              type="text"
              value={email}
              error={error}
              onChange={onEmailChangeHandler}
              onKeyDown={onEmailKeyDownHandler}
            />
            <InputBox
              ref={passwordRef}
              name="password"
              placeholder="password"
              type={passwordType}
              error={error}
              value={password}
              onChange={onPasswordChangeHandler}
              onIconClick={onPasswordIconClickHandler}
              onKeyDown={onPasswordKeyDownHandler}
              icon={passwordIcon}
            />
          </div>
          <div className="auth-card-bottom">
            {error && (
              <div className="auth-sign-in-error-box">
                <div className="auth-sign-in-error-message">
                  {`이메일 주소 또는 비밀번호를 잘못 입력했습니다.
                            입력하신 내용을 다시 확인해주세요.`}
                </div>
              </div>
            )}
            <div className="auth-card-bottom-a">
              <div className="auth-card-save-email-box">
                <CheckBox
                  id="savedEmail"
                  name="savedEmail"
                  label="이메일 저장"
                  checked={savedId}
                  onChange={onCheckBoxChangeHandler}
                />
              </div>
              <div className="auth-card-find-info-box">
                <a href="/">아이디</a>/<a href="/">비밀번호 찾기</a>
              </div>
            </div>
            <div className="auth-card-bottom-b">
              <div className="auth-card-sign-in-btn" onClick={onSignInButtonClickHandler}>
                {'Login'}
              </div>
              <div className="icon auth-card-social-in-btn" onClick={onSocialLoginClickHandler}></div>
            </div>
            <div className="auth-desc-box">
              <div className="auth-desc">
                {'신규 사용자이신가요?'}
                <span className="auth-desc-link" onClick={onSignUpClickHandler}>
                  {'회원 가입'}
                </span>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  // component: 회원가입 컴포넌트 //
  const SignUpCard = () => {
    // comment: 요소 참조 상태 - key down 이벤트용 //
    // state: 이름 요소 참조 상태 //
    const nameRef = useRef<HTMLInputElement | null>(null);

    // state: 닉네임 요소 참조 상태 //
    const nicknameRef = useRef<HTMLInputElement | null>(null);

    // state: 전화버호 요소 참조 상태 //
    const phoneRef = useRef<HTMLInputElement | null>(null);

    // state: 이메일 요소 참조 상태 //
    const emailRef = useRef<HTMLInputElement | null>(null);

    // state: 비밀번호 요소 참조 상태 //
    const passwordRef = useRef<HTMLInputElement | null>(null);

    // state: 비밀번호 확인 요소 참조 상태 //
    const passwordCheckRef = useRef<HTMLInputElement | null>(null);

    // state: 개인 정보 동의 요소 참조 상태 //
    const agreeRef = useRef<HTMLInputElement | null>(null);

    // comment: 요소 상태 //
    // state: 이름 요소 상태 //
    const [name, setName] = useState<string>('');

    // state: 닉네임 요소 상태 //
    const [nickname, setNickname] = useState<string>('');

    // state: 전화번호 요소 상태 //
    const [phone, setPhone] = useState<string>('');

    // state: 이메일 value 상태 //
    const [email, setEmail] = useState<string>('');

    // state: 비밀번호 value 상태 //
    const [password, setPassword] = useState<string>('');

    // state: 비밀번호 확인 요소 상태 //
    const [passwordCheck, setPasswordCheck] = useState<string>('');

    // state: 비밀번호 표시/숨김 아이콘 상태 //
    const [passwordIcon, setPasswordIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>(
      'eye-light-off-icon',
    );

    // state: 비밀번호 표시/숨김 아이콘 상태 //
    const [passwordCheckIcon, setPasswordCheckIcon] = useState<
      'eye-light-off-icon' | 'eye-light-on-icon'
    >('eye-light-off-icon');

    // state: 개인정보 동의 요소 상태 //
    const [agree, setAgree] = useState<boolean>(false);

    // state: 에러 상태 //
    const [error, setError] = useState<boolean>(false);

    // state: 비밀번호 type 상태 //
    const [passwordType, setPasswordType] = useState<'text' | 'password'>('password');

    // state: 비밀번호 확인 type 상태 //
    const [passwordCheckType, setPasswordCheckType] = useState<'text' | 'password'>('password');

    // event-handler: 회원가입 버튼 click 이벤트 처리 //
    const onSignUpButtonClickHandler = () => {
      console.log('회원가입');
    };

    // event-handler: 회원가입 링크 click 이벤트 처리 //
    const onSignInClickHandler = () => {
      setView('sign-in');
    };

    

    // event-handler: 이름 변경 이벤트 처리 //
    const onNameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setName(value);
    };

    // event-handler: 닉네임 변경 이벤트 처리 //
    const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setNickname(value);
    };

    // event-handler: 전화번호 변경 이벤트 처리 //
    const onPhoneChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setPhone(value);
    };

    // event-handler: 이메일 변경 이벤트 처리 //
    const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setEmail(value);
    };

    // event-handler: 비밀번호 변경 이벤트 처리 //
    const onPasswordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setPassword(value);
    };

    // event-handler: 비밀번호 확인 변경 이벤트 처리 //
    const onPassworCheckChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setError(false);
      const { value } = event.target;
      setPasswordCheck(value);
    };

    // event-handler: 개인정보 제공 동의 change 이벤트 처리 //
    const onCheckBoxChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const isChecked = event.target.checked;
      setAgree(isChecked);
    };

    // event-handler: 이름 input key down 이벤트 처리 //
    const onNameKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!nicknameRef.current) return;
      nicknameRef.current.focus();
    };

    // event-handler: 닉네임 input key down 이벤트 처리 //
    const onNicknameKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!phoneRef.current) return;
      phoneRef.current.focus();
    };

    // event-handler: 전화번호 input key down 이벤트 처리 //
    const onPhoneKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!phoneRef.current) return;
      phoneRef.current.focus();
    };

    // event-handler: 이메일 input key-down 이벤트 처리//
    const onEmailKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!passwordRef.current) return;
      passwordRef.current.focus();
    };

    // event-handler: 비밀번호 input key-down 이벤트 처리 //
    const onPasswordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!passwordCheckRef.current) return;
      passwordCheckRef.current.focus();
    };

    // event-handler: 비밀번호 확인 input key down 이벤트 처리 //
    const onPasswordCheckKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      if (!agreeRef.current) return;
      agreeRef.current.focus();
    };

    // event-handler: 개인 정보 동의 input key down 이벤트 처리 //
    const onAgreeKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) => {
      if (event.key !== 'Enter') return;
      onSignUpButtonClickHandler();
    };

    // event-handler: 닉네임 중복 확인 click 이벤트 처리 //
    const onNicknameClickHandler = async () => {
      if (nickname.trim() === '') return;
      const response = await nicknameDupCheckRequest(nickname);
      console.log(response);

      if (response?.status === '200') {
        alert('사용 가능한 닉네임 입니다.');
      } else {
        alert('중복된 닉네임 입니다');
      }
    };

    // event-handler: 비밀번호 표시/숨김 아이콘 버튼 click 이벤트 처리 //
    const onPasswordIconClickHandler = () => {
      if (passwordType === 'text') {
        setPasswordType('password');
        setPasswordIcon('eye-light-off-icon');
      } else {
        setPasswordType('text');
        setPasswordIcon('eye-light-on-icon');
      }
    };

    // event-handler: 비밀번호 확인 표시/숨김 아이콘 버튼 click 이벤트 처리 //
    const onPasswordCheckIconClickHandler = () => {
      if (passwordCheckType === 'text') {
        setPasswordCheckType('password');
        setPasswordCheckIcon('eye-light-off-icon');
      } else {
        setPasswordCheckType('text');
        setPasswordCheckIcon('eye-light-on-icon');
      }
    };

    // render: 회원가입 컴포넌트 랜더링 //
    return (
      <div className="auth-card">
        <div className="auth-card-box">
          <div className="auth-card-top">
            <InputBox
              ref={nameRef}
              name="name"
              type="text"
              error={error}
              placeholder="이름"
              value={name}
              onChange={onNameChangeHandler}
              onKeyDown={onNameKeyDownHandler}
            />
            <div className="nickname-dup-check-box">
              <InputBox
                ref={nicknameRef}
                name="nickname"
                type="text"
                error={error}
                placeholder="닉네임"
                value={nickname}
                onChange={onNicknameChangeHandler}
                onKeyDown={onNicknameKeyDownHandler}
              />
              <ButtonBox label="중복 확인" type="button" onClick={onNicknameClickHandler} />
            </div>
            <div className="phone-verify-box">
              <InputBox
                ref={phoneRef}
                name="phone"
                type="text"
                error={error}
                placeholder="휴대전화 번호"
                value={phone}
                onChange={onPhoneChangeHandler}
                onKeyDown={onPhoneKeyDownHandler}
              />
              <ButtonBox label="번호 인증" type="button" />
            </div>
            <InputBox
              ref={emailRef}
              name="email"
              placeholder="이메일"
              type="text"
              value={email}
              error={error}
              onChange={onEmailChangeHandler}
              onKeyDown={onEmailKeyDownHandler}
            />
            <InputBox
              ref={passwordRef}
              name="password"
              placeholder="비밀번호"
              type={passwordType}
              error={error}
              value={password}
              onChange={onPasswordChangeHandler}
              onIconClick={onPasswordIconClickHandler}
              onKeyDown={onPasswordKeyDownHandler}
              icon={passwordIcon}
            />
            <InputBox
              ref={passwordCheckRef}
              name="passwordCheck"
              placeholder="비밀번호 확인"
              type={passwordCheckType}
              error={error}
              value={passwordCheck}
              onChange={onPassworCheckChangeHandler}
              onIconClick={onPasswordCheckIconClickHandler}
              onKeyDown={onPasswordCheckKeyDownHandler}
              icon={passwordCheckIcon}
            />
          </div>
          <div className="auth-card-bottom">
            <CheckBox
              id="agree"
              label="개인정보 동의"
              name="agree"
              checked={agree}
              onChange={onCheckBoxChangeHandler}
              onKeyDown={onAgreeKeyDownHandler}
            />
            <ButtonBox label={'가입하기'} type={'submit'} />
          </div>
          <div className="auth-desc-box">
            <div className="auth-desc">
              {'이미 가입하신 계정이 있으신가요?'}
              <span className="auth-desc-link" onClick={onSignInClickHandler}>
                {'로그인'}
              </span>
            </div>
          </div>
        </div>
      </div>
    );
  };

  // render: 인증 화면 컴포넌트 랜더링 //
  return (
    <div id="auth-wrapper">
      <div className="auth-contianer">
        {view === 'sign-in' && <SignInCard />}
        {view === 'sign-up' && <SignUpCard />}
      </div>
    </div>
  );
}

export default AuthServer;
