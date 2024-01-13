import './style.css';
import {
  nicknameDupCheckRequest,
  phoneValidateAtSignUpRequest,
  signInRequest,
  signUpRequest,
} from 'apis';
import { useState, useRef, KeyboardEvent, ChangeEvent, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { SignInRequestDto, SignUpRequestDto } from 'apis/request/auth';
import { SignInResponseDto } from 'apis/response/auth';
import { ResponseDto } from 'apis/response';
import InputBox from 'components/InputBox';
import CheckBox from 'components/CheckBox';
import { AUTH_PATH, MAIN_PATH } from 'constant';
import ResponseCode from 'constant/response-code.enum';
import { MessageType } from 'types/interface';
import ReactModal from 'react-modal';
import axios from 'axios';
import certNumStore from 'stores/cert-num.store';
import PhoneValidateResponseAtSignUpDto from 'apis/response/auth/phone-validate-at-signup.response.dto';

// component: 인증 화면 컴포넌트 //
function AuthServer() {
  // state: 페이지 상태 //
  const [view, setView] = useState<'sign-in' | 'sign-up'>('sign-in');

  // function: 네비게이터 함수 //
  const navigator = useNavigate();

  // component: 로그인 컴포넌트 //
  const SignInCard = () => {
    // state: 이메일 요소 참조 상태 //
    const emailRef = useRef<HTMLInputElement | null>(null);

    // state: 비밀번호 요소 참조 상태 //
    const passwordRef = useRef<HTMLInputElement | null>(null);

    // state: 이메일 value 상태 //
    const [email, setEmail] = useState<string>('');

    // state: 이메일 오류 메시지 //
    const [emailErrorMsg, setEmailErrorMsg] = useState<string>('');

    // state: 비밀번호 value 상태 //
    const [password, setPassword] = useState<string>('');

    // state: 비밀번호 type 상태 //
    const [passwordType, setPasswordType] = useState<'text' | 'password'>('password');

    // state: 비밀번호 표시/숨김 아이콘 상태 //
    const [passwordIcon, setPasswordIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>(
      'eye-light-off-icon',
    );

    // state: 비밀번호 오류 메시지 //
    const [pwErrorMsg, setPwErrorMsg] = useState<string>('');

    // event-handler: 이메일 변경 이벤트 처리 //
    const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setEmailErrorMsg('');
      const { value } = event.target;
      setEmail(value);
    };

    // event-handler: 비밀번호 변경 이벤트 처리 //
    const onPasswordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setPwErrorMsg('');
      const { value } = event.target;
      setPassword(value);
    };

    // function: sign in response 처리 함수//
    const signInResponse = (responseBody: SignInResponseDto | ResponseDto | null) => {
      // comment: 서버가 안켜진 경우 또는 도메인 주소가 잘못된 경우 //
      console.dir(responseBody);
      if (!responseBody) {
        alert('네트워크 연결 상태를 확인해주세요!');
        return;
      }

      const { code, message } = responseBody;

      if (code === ResponseCode.TEMPORARY_SERVER_ERROR) {
        alert(message);
        return;
      }

      if (
        code === ResponseCode.SIGN_IN_FAIL ||
        code === ResponseCode.VALIDATION_FAIL ||
        code === ResponseCode.NOT_FOUND_USER ||
        code === ResponseCode.NOT_CORRECT_PASSWORD
      ) {
        setPwErrorMsg('이메일 주소 또는 비밀번호를 잘못 입력했습니다.');
        return;
      }

      // uuid 와 role 을 세션 스토리지에 저장.
      const responseBodyWithUserInfo = responseBody as SignInResponseDto;
      if (responseBodyWithUserInfo.content) {
        window.sessionStorage.setItem('uuid', responseBodyWithUserInfo.content.uuid);

        window.sessionStorage.setItem('role', responseBodyWithUserInfo.content.role);
      }

      navigator(MAIN_PATH());
    };

    // function: 로그인 시 validation 기능 //
    const signInValidation = (dto: SignInRequestDto) => {
      if (dto.email.trim() === '') {
        setEmailErrorMsg('이메일을 입력바랍니다.');
        return false;
      } else if (dto.password.trim() === '') {
        setPwErrorMsg('비밀번호를 입력 바랍니다.');
        return false;
      }
      return true;
    };

    // event-handler: 로그인 버튼 click 이벤트 처리 //
    const onSignInButtonClickHandler = async () => {
      const requestBody: SignInRequestDto = { email, password };

      if (signInValidation(requestBody)) {
        await signInRequest(requestBody).then(signInResponse);
      }
    };

    // event-handler: 소셜 로그인 button click 이벤트 처리//
    const onSocialLoginClickHandler = () => {
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

    // event-handler: 아이디 찾기 click 이벤트 처리 //
    const onFindIdClickHandler = () => {
      navigator('/find-id');
    };

    // event-handler: 비밀번호 찾기 click 이벤트 처리 //
    const onFindPwClickHandler = () => {
      navigator('/find-pw');
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
              error={emailErrorMsg !== ''}
              message={emailErrorMsg}
              onChange={onEmailChangeHandler}
              onKeyDown={onEmailKeyDownHandler}
            />
            <InputBox
              ref={passwordRef}
              name="password"
              placeholder="password"
              type={passwordType}
              error={pwErrorMsg !== ''}
              message={pwErrorMsg}
              value={password}
              onChange={onPasswordChangeHandler}
              onIconClick={onPasswordIconClickHandler}
              onKeyDown={onPasswordKeyDownHandler}
              icon={passwordIcon}
            />
          </div>
          <div className="auth-card-bottom">
            <div className="auth-card-bottom-a">
              <div className="auth-card-btn" onClick={onSignInButtonClickHandler}>
                {'Login'}
              </div>
              <div
                className="icon auth-card-social-in-btn"
                onClick={onSocialLoginClickHandler}
              ></div>
            </div>
            <div className="auth-card-bottom-b">
              <div className="auth-card-find-info-box">
                <a onClick={onFindIdClickHandler}>아이디</a>/
                <a onClick={onFindPwClickHandler}>비밀번호 찾기</a>
              </div>
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

    // state: 이름 에러 상태 //
    const [isNameError, setNameError] = useState<boolean>(false);

    // state: 닉네임 요소 상태 //
    const [nickname, setNickname] = useState<string>('');

    // state: 닉네임 에러 상태 //
    const [isNicknameError, setNicknameError] = useState<boolean>(false);

    // state: 전화번호 요소 상태 //
    const [phone, setPhone] = useState<string>('');

    // state: 전화번호 에러 상태 //
    const [isPhoneError, setPhoneError] = useState<boolean>(false);

    // state: 이메일 요소 상태 //
    const [email, setEmail] = useState<string>('');

    // state: 이메일 에러 상태 //
    const [isEmailError, setEmailError] = useState<boolean>(false);

    // state: 비밀번호 요소 상태 //
    const [pw, setPw] = useState<string>('');

    // state: 비밀번호 에러 상태 //
    const [isPwError, setPwError] = useState<boolean>(false);

    // state: 비밀번호 확인 요소 상태 //
    const [pwRe, setPwRe] = useState<string>('');

    // state: 비밀번호 확인 에러 상태 //
    const [isPwReError, setPwReError] = useState<boolean>(false);

    // state: 에러 메시지 //
    const [message, setMessage] = useState<MessageType>({
      name: '',
      nickname: '',
      phone: '',
      email: '',
      pw: '',
      pwRe: '',
      agree: '',
    });

    // state: 비밀번호 type 상태 //
    const [pwType, setPwType] = useState<'text' | 'password'>('password');

    // state: 비밀번호 확인 type 상태 //
    const [pwReType, setPwReType] = useState<'text' | 'password'>('password');

    // state: 비밀번호 표시/숨김 아이콘 상태 //
    const [pwIcon, setPwIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>(
      'eye-light-off-icon',
    );

    // state: 비밀번호 확인 표시/숨김 아이콘 상태 //
    const [pwReIcon, setPwReIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>(
      'eye-light-off-icon',
    );

    // state: 개인정보 동의 요소 상태 //
    const [agree, setAgree] = useState<boolean>(false);

    // comment: 가입하기 버튼 활성화 요소//
    // state: 닉네임 중복 여부 확인 상태 //
    const [isDuplicated, setIsDuplicated] = useState<boolean>(true);

    //state: 번호 인증 여부 확인 상태 //
    const [isValidation, setValidationCheck] = useState<boolean>(false);

    // state: 가입 가능 여부 상태 //
    const [signUpEnable, setSignUpEnable] = useState<boolean>(false);

    // state: phone check popoup 여부 상태 //
    const [isMobileCheckPopupOpen, setMobileCheckPopupOpen] = useState<boolean>(false);

    const [SMSError, setSMSError] = useState<string>('');

    const [code, setCode] = useState<string>('');

    // store: 휴대전화 인증 번호 store//
    const { certNum, setCertNum } = certNumStore();

    // event-handler: 회원가입 링크 click 이벤트 처리 //
    const onSignInClickHandler = () => {
      setView('sign-in');
    };

    // event-handler: 이름 변경 이벤트 처리 //
    const onNameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setNameError(false);
      message.name = '';
      const { value } = event.target;
      setName(value);
    };

    // event-handler: 닉네임 변경 이벤트 처리 //
    const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setNicknameError(false);
      message.nickname = '';
      const { value } = event.target;
      setNickname(value);
    };

    // effect: 닉네임 변경을 감지하여 중복 체크 //
    useEffect(() => {
      const fetchData = async () => {
        const response = await nicknameDupCheckRequest(nickname);

        if (!response) return;

        const { code } = response;

        if (code === ResponseCode.OK) {
          setIsDuplicated(false);
          return;
        } else if (code === ResponseCode.DUPLICATED_NICKNAME) {
          setIsDuplicated(true);
          return;
        }
      };

      if (nickname.trim() !== '') {
        fetchData();
      }
    }, [nickname]);

    // event-handler: 전화번호 변경 이벤트 처리 //
    const onPhoneChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setPhoneError(false);
      message.phone = '';
      const { value } = event.target;
      setPhone(value);
    };

    // event-handler: 이메일 변경 이벤트 처리 //
    const onEmailChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setEmailError(false);
      message.email = '';
      const { value } = event.target;
      setEmail(value);
    };

    // event-handler: 비밀번호 변경 이벤트 처리 //
    const onPasswordChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setPwError(false);
      message.pw = '';
      const { value } = event.target;
      setPw(value);
    };

    // event-handler: 비밀번호 확인 변경 이벤트 처리 //
    const onPassworCheckChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setPwReError(false);
      message.pwRe = '';
      const { value } = event.target;
      setPwRe(value);
    };

    // event-handler: 개인정보 제공 동의 change 이벤트 처리 //
    const onCheckBoxChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      const isChecked = event.target.checked;
      setAgree(isChecked);
      if (isChecked) message.agree = '';
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

    // event-handler: 비밀번호 표시/숨김 아이콘 버튼 click 이벤트 처리 //
    const onPasswordIconClickHandler = () => {
      if (pwType === 'text') {
        setPwType('password');
        setPwIcon('eye-light-off-icon');
      } else {
        setPwType('text');
        setPwIcon('eye-light-on-icon');
      }
    };

    // event-handler: 비밀번호 확인 표시/숨김 아이콘 버튼 click 이벤트 처리 //
    const onPasswordCheckIconClickHandler = () => {
      if (pwReType === 'text') {
        setPwReType('password');
        setPwReIcon('eye-light-off-icon');
      } else {
        setPwReType('text');
        setPwReIcon('eye-light-on-icon');
      }
    };

    // event-handler: 번호 인증 버튼 click 이벤트 처리 //
    const onValidationCheckClickHandler = async () => {
      const phoneRegex = /^01(0|1|[6-8])\d{3,4}\d{4}$/;

      if (!phone) {
        setPhoneError(true);
        //'휴대폰 번호를 입력해주세요'
        setMessage((prev) => ({ ...prev, phone: '휴대폰 번호를 입력해주세요' }));
        return;
      } else if (phone !== '' && !phoneRegex.test(phone.trim())) {
        setMessage((prev) => ({ ...prev, phone: '-없이 숫자만 입력' }));
        return;
      }

      const response = await phoneValidateAtSignUpRequest(phone);

      if (!response) {
        alert('네트워크 연결 상태를 확인해주세요');
        return;
      }

      const successResponse = response as PhoneValidateResponseAtSignUpDto;

      if (successResponse.content) {
        setCertNum(successResponse.content);
        alert('✅ 인증정보가 전송되었습니다. 인증번호를 입력해주세요');
        setMobileCheckPopupOpen(true);
        return;
      } else {
        alert('😒 휴대전화 번호를 잘못 입력하셨습니다 다시 입력 바랍니다');
      }
    };

    // event-handler: 인증 번호 확인 버튼 click 이벤트 처리 //
    const handleSendPhoneForID = async () => {
      if (!code) {
        setSMSError('인증번호를 입력하세요.');
        return;
      }

      if (certNum.toString() !== code) {
        setSMSError('인증번호가 일치하지 않습니다.');
        return;
      }

      try {
        await axios.post(
          'auth-server/api/v1/send/id/phone',
          { phoneNumber: phone },
          { headers: { 'Content-Type': 'application/json' } },
        );
        setSMSError('');
        alert('🎉 인증정보가 일치합니다.다음페이지로 이동하겠습니다.');
        setMobileCheckPopupOpen(false);
        setValidationCheck(true);
      } catch (err) {
        if (axios.isAxiosError(err) && err.response) {
          setSMSError(err.response.data.message || '인증정보가 일치하지 않습니다.');
        } else {
          setSMSError('An unknown error occurred');
        }
      }
    };

    // event-handler: 닫기 버튼 click event handler//
    const onCloseBtnClickHandler = () => {
      setMobileCheckPopupOpen(false);
    };

    // function: 회원 가입 시 에러메시지 처리 기능 //
    const signUpVfMsgResponse = (responseBody: string[]) => {
      setMessage({
        name: '',
        nickname: '',
        phone: '',
        email: '',
        pw: '',
        pwRe: '',
        agree: '',
      });

      responseBody.map((e) => {
        const message = e.split(' : ');
        setMessage((prev) => ({ ...prev, [message[0]]: message[1] }));
      });
    };

    // function: 회원 가입 후 응답처리 기능 //
    const signUpResponse = (responseBody: ResponseDto | null) => {
      // comment: 서버가 안켜진 경우 또는 도메인 주소가 잘못된 경우 //
      if (!responseBody) {
        alert('네트워크 연결 상태를 확인해주세요!');
        return;
      }

      const { code, message } = responseBody;
      console.log(code);
      console.log(message);

      if (code === ResponseCode.USER_CREATED) {
        navigator(AUTH_PATH());
      } else {
        return;
      }
    };

    // function: 회원 가입 시 validation 기능 //
    const signUpValidation = (dto: SignUpRequestDto) => {
      if (dto.name.trim() === '') {
        console.log('이름을 입력하지 않았습니다.');
        setNameError(true);
        setMessage((prev) => ({ ...prev, name: '이름을 입력해주세요.' }));
        return;
      } else if (dto.nickname.trim() === '') {
        console.log('닉네임을 입력하지 않았습니다.');
        setNicknameError(true);
        setMessage((prev) => ({ ...prev, nickname: '닉네임을 입력해주세요' }));
        return;
      } else if (dto.phone.trim() === '') {
        console.log('휴대전화 번호를 입력하지 않았습니다.');
        setPhoneError(true);
        setMessage((prev) => ({ ...prev, phone: '휴대전화 번호를 입력해주세요.' }));
        return;
      } else if (dto.email.trim() === '') {
        console.log('이메일을 입력하지 않았습니다.');
        setEmailError(true);
        setMessage((prev) => ({ ...prev, email: '이메일을 입력해주세요.' }));
        return;
      } else if (dto.pw.trim() === '') {
        console.log('비밀번호를 입력하지 않았습니다.');
        setPwError(true);
        setMessage((prev) => ({ ...prev, pw: '비밀번호를 입력해주세요.' }));
        return;
      } else if (dto.pwRe.trim() === '') {
        console.log('비밀번호를 입력하지 않았습니다.');
        setPwReError(true);
        setMessage((prev) => ({ ...prev, pwRe: '비밀번호 확인을 입력해주세요.' }));
        return;
      } else if (!dto.agree) {
        console.log('개인정보 제공 동의를 하지 않았습니다.');
        setMessage((prev) => ({ ...prev, agree: '개인 정보 제공을 동의 해주세요.' }));
        return;
      }
    };

    // event-handler: 가입하기 버튼 click 이벤트 처리 //
    const onSignUpButtonClickHandler = async () => {
      const requestBody: SignUpRequestDto = {
        name,
        nickname,
        phone,
        email,
        pw,
        pwRe,
        agree,
      };

      signUpValidation(requestBody);

      const regex = /^[가-힣]*$/;

      if (!regex.test(name.trim())) {
        setNameError(true);
        setMessage((prev) => ({ ...prev, name: '이름을 한글로 입력 바랍니다.' }));
      }

      if (pw !== pwRe) {
        setMessage((prev) => ({ ...prev, pwRe: '비밀번호가 일치하지 않습니다' }));
        return;
      }

      const phoneRegex = /^01(0|1|[6-8])\d{3,4}\d{4}$/;

      if (phone !== '' && !phoneRegex.test(phone.trim())) {
        setMessage((prev) => ({ ...prev, phone: '-없이 숫자만 입력' }));
      }

      const response = await signUpRequest(requestBody);
      if (response) {
        if (response.vfMessages) {
          signUpVfMsgResponse(response.vfMessages);
          return;
        }
        signUpResponse(response);
      }
    };

    // effect: 번호 인증 및 닉네임 중복 처리 완료 시 가입하기 버튼 활성화
    useEffect(() => {
      // 휴대전화 인증 여부 상태값 if 조건문에 추가!
      if (!isDuplicated) {
        setSignUpEnable(true);
      } else {
        setSignUpEnable(false);
      }
    }, [isDuplicated, isValidation]);

    // render: 회원가입 컴포넌트 랜더링 //
    return (
      <div className="auth-card">
        <div className="auth-card-box">
          <div className="auth-card-top">
            <InputBox
              ref={nameRef}
              name="name"
              type="text"
              error={isNameError}
              placeholder="이름*"
              value={name}
              onChange={onNameChangeHandler}
              onKeyDown={onNameKeyDownHandler}
              message={message.name}
            />
            <div className="auth-sign-up-valid-box">
              <InputBox
                ref={nicknameRef}
                name="nickname"
                type="text"
                error={isNicknameError}
                placeholder="닉네임*"
                value={nickname}
                onChange={onNicknameChangeHandler}
                onKeyDown={onNicknameKeyDownHandler}
                message={message.nickname}
                hasP={true}
                dupCheck={isDuplicated}
              />
            </div>
            <InputBox
              ref={phoneRef}
              name="phone"
              type="text"
              error={isPhoneError}
              placeholder="휴대전화 번호*"
              value={phone}
              onChange={onPhoneChangeHandler}
              onKeyDown={onPhoneKeyDownHandler}
              message={message.phone}
              btnVlaue="번호인증"
              onBtnClick={onValidationCheckClickHandler}
            />

            <InputBox
              ref={emailRef}
              name="email"
              placeholder="이메일*"
              type="text"
              value={email}
              error={isEmailError}
              onChange={onEmailChangeHandler}
              onKeyDown={onEmailKeyDownHandler}
              message={message.email}
            />
            <InputBox
              ref={passwordRef}
              name="password"
              placeholder="비밀번호*"
              type={pwType}
              error={isPwError}
              value={pw}
              onChange={onPasswordChangeHandler}
              onIconClick={onPasswordIconClickHandler}
              onKeyDown={onPasswordKeyDownHandler}
              icon={pwIcon}
              message={message.pw}
            />
            <InputBox
              ref={passwordCheckRef}
              name="passwordCheck"
              placeholder="비밀번호 확인*"
              type={pwReType}
              error={isPwReError}
              value={pwRe}
              onChange={onPassworCheckChangeHandler}
              onIconClick={onPasswordCheckIconClickHandler}
              onKeyDown={onPasswordCheckKeyDownHandler}
              icon={pwReIcon}
              message={message.pwRe}
            />
          </div>
          <div className="auth-card-bottom">
            <CheckBox
              id="agree"
              label="개인정보 제공 동의*"
              name="agree"
              checked={agree}
              onChange={onCheckBoxChangeHandler}
              onKeyDown={onAgreeKeyDownHandler}
              message={message.agree}
            />
            {signUpEnable ? (
              <div className="auth-card-btn" onClick={onSignUpButtonClickHandler}>
                {'가입하기'}
              </div>
            ) : (
              <div className="auth-card-btn-disabled" aria-disabled={!signUpEnable}>
                {'가입하기'}
              </div>
            )}
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
        <ReactModal
          overlayClassName={'modal-overlay'}
          className={'modal-content'}
          isOpen={isMobileCheckPopupOpen}
          onRequestClose={() => {
            setMobileCheckPopupOpen(false);
          }}
          ariaHideApp={false}
          contentLabel={'Pop up Message'}
          shouldCloseOnOverlayClick={false}
        >
          <div>
            <button
              className="close-btn"
              title="close"
              type="button"
              onClick={onCloseBtnClickHandler}
            />
            <div style={{ display: 'flex', flexDirection: 'column', height: '110px' }}>
              <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
                <input
                  type="text"
                  value={code}
                  onChange={(e) => {
                    setCode(e.target.value);
                    setSMSError('');
                  }}
                  placeholder="인증번호 입력"
                />
                <button onClick={handleSendPhoneForID}>확인</button>
              </div>
              <p style={{ color: 'red', marginLeft: '13px' }}>{SMSError}</p>
            </div>
          </div>
        </ReactModal>
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
