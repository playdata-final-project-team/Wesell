import InputBox from 'components/InputBox';
import { useState, useRef, KeyboardEvent } from 'react';
import './style.css'
import CheckBox from 'components/CheckBox';

// component: 인증 화면 컴포넌트 //
function AuthServer(){

     // state: 페이지 상태 //
     const [view, setView] = useState<'sign-in' | 'sign-up'>('sign-in');

// component: 로그인 컴포넌트 //
const SignInCard = () => {

    // state: 이메일 요소 참조 상태 //
    const emailRef = useRef<HTMLInputElement | null>(null);

    // state: 비밀번호 요소 참조 상태 //
    const passwordRef = useRef<HTMLInputElement | null>(null);

    // state: 이메일 value 상태 //
    const [email,setEmail] = useState<string>('');

    // state: 비밀번호 value 상태 //
    const [password,setPassword] = useState<string>('');

    // state: 비밀번호 type 상태 //
    const [passwordType, setPasswordType] = useState<'text' | 'password'>('password');

    // state: 비밀번호 표시/숨김 아이콘 상태 //
    const [passwordIcon, setPasswordIcon] = useState<'eye-light-off-icon' | 'eye-light-on-icon'>('eye-light-off-icon');

    // state: 체크박스 check 상태 //
    // const [isChecked, setIsChecked] = useState<boolean>(false);

    // state: 에러 상태 //
    const [error, setError] = useState<boolean>(false);

   // event-handler: 로그인 버튼 click 이벤트 처리 //
    const onSignInButtonClickHandler = () => {console.log('로그인')}

    // event-handler: 비밀번호 표시/숨김 아이콘 버튼 click 이벤트 처리 //
    const onPasswordIconClickHandler = () => {
        if(passwordType === 'text'){
            setPasswordType('password');
            setPasswordIcon('eye-light-off-icon');
        }else{
            setPasswordType('text');
            setPasswordIcon('eye-light-on-icon');
        }
    }

    // event-handler: 회원가입 링크 click 이벤트 처리 //
    const onSignUpClickHandler = () =>{
        setView('sign-up');
    }

    // event-handler: 이메일 input key-down 이벤트 처리//
    const onEmailKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) =>{
        if(event.key !== 'Enter') return;
        if(!passwordRef.current) return;
        passwordRef.current.focus();
    }

    // event-handler: 비밀번호 input key-down 이벤트 처리 //
    const onPasswordKeyDownHandler = (event: KeyboardEvent<HTMLInputElement>) =>{
        if(event.key !== 'Enter') return;
        onSignInButtonClickHandler();
    }

    // render: 로그인 컴포넌트 랜더링 //
    return(
        <div className='auth-card'>
            <div className='auth-card-box'>
                <div className='auth-card-top'>
                    <InputBox ref={emailRef} name='email' placeholder='e-mail' type='text' value={email} error={error} setValue={setEmail} onKeyDown={onEmailKeyDownHandler}/>
                    <InputBox ref={passwordRef} name='password' placeholder='password' type={passwordType} error={error} value={password} setValue={setPassword} onIconClick={onPasswordIconClickHandler} onKeyDown={onPasswordKeyDownHandler} icon={passwordIcon}/>
                </div>
                <div className='auth-card-bottom'>
                    {error &&
                    <div className='auth-sign-in-error-box'>
                        <div className='auth-sign-in-error-message'>
                            {`이메일 주소 또는 비밀번호를 잘못 입력했습니다.
                            입력하신 내용을 다시 확인해주세요.`}
                        </div>
                    </div>
                    }
                    <div className='auth-card-bottom-a'>
                        <div className='auth-card-save-email-box'>
                            <CheckBox id='savedEmail' name='savedEmail' label='이메일 저장'/>
                        </div>
                        <div className='auth-card-find-info-box'>
                            <a href='/'>아이디</a>/<a href='/'>비밀번호 찾기</a>
                        </div>
                    </div>
                    <div className='auth-card-bottom-b'>
                        <div className='auth-card-sign-in-btn' onClick={onSignInButtonClickHandler}>{'Login'}</div>
                        <div className='icon auth-card-social-in-btn'></div>
                    </div>
                    <div className='auth-desc-box'>
                        <div className='auth-desc'>{'신규 사용자이신가요?'}<span className='auth-desc-link' 
                        onClick={onSignUpClickHandler}>{'회원 가입'}</span></div>
                    </div>
                </div>
            </div>
        </div>
    );
}

// component: 회원가입 컴포넌트 //
const SignUpCard = () => {


    // render: 회원가입 컴포넌트 랜더링 //
    return(
        <>
        </>
    );
}
    
    // render: 인증 화면 컴포넌트 랜더링 //
    return(
        <div id='auth-wrapper'>
            <div className='auth-contianer'>
                {view === 'sign-in' && <SignInCard />}
                {view === 'sign-up' && <SignUpCard />}
            </div>
        </ div>
    );
}

export default AuthServer;