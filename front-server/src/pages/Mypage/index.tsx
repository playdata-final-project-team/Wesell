import InputBox from "components/InputBox";
import ABox from "components/aBox";
import { AUTH_PATH, MAIN_PATH, WITHDRAW_PATH } from "constant";
import { ChangeEvent, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import './style.css';
import useStore from "stores";
import { mypageInfoRequest } from "apis";
import MypageResponseDto from "apis/response/mypage/mypage.response.dto";
import { ResponseDto } from "apis/response";

function Mypage(){

    const [view,setView] = useState<'user-info' | 'deal-info'>('user-info');


    // component: 마이페이지 - 회원정보 컴포넌트 //
    const UserInfo = () => {

        // state: 닉네임 상태값//
        const [nickname, setNickname] = useState<string>('');
        // state: 이메일 상태값 //
        const [email, setEmail] = useState<string>('');
        // state: 전화번호 상태값 //
        const [phone, setPhone] = useState<string>('');
        // state: 이름 상태값 //
        const [name, setName] = useState<string>('');

        // state: 이름 오류 상태값 //
        const [isNameError, setNameError] = useState<boolean>(false);

        // state: 이름 관련 오류 메시지 상태값 //
        const [nameErrorMsg, setNameErrorMsg] = useState<string>('');

        // context: uuid, role 값//
        const uuid = useStore((state) => state.uuid);
        const role = useStore((state) => state.role);

        // function: navaigation 함수 //
        const navigator = useNavigate();

        // effect: 페이지 리렌더링 시마다 확인 //
        useEffect(() => {

            if(uuid === null || role === null){
                // alert("로그인 바랍니다.");
                // navigator("/");
                return;
            }

            // uuid 값 회원 정보 조회하기
            const feignData = async (uuid : string) => {
                const responseBody : MypageResponseDto | null = await mypageInfoRequest(uuid);
                
                if (!responseBody) {
                    alert('네트워크 연결 상태를 확인해주세요!');
                    return;
                }

                if('email' in responseBody){
                    setEmail(responseBody.email);
                }

                if('name' in responseBody){
                    setName(responseBody.name);
                }

                if('nickname' in responseBody){
                    setNickname(responseBody.nickname);
                }

                if('phone' in responseBody){
                    setPhone(responseBody.phone);
                }
            }

            feignData(uuid);

        },[]);
        
        // event-handler: 이름 on-change 이벤트 핸들링 //
        const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
            setNameError(false);
            setNameErrorMsg('');
            const {value} = event.target;
            setName(value);
        }

        // event-handler: 판매내역 on-click 이벤트 핸들링//
        const onDealInfoBtnClickHandler = () =>{
            setView('deal-info');
        }

        // function: 회원정보 수정 후 처리 함수//
        const updateMyInfoResponse = (responseBody: ResponseDto | null) => {
            if (!responseBody) {
                alert('네트워크 연결 상태를 확인해주세요!');
                return;
              }
        };

        // event-handler: 수정하기 on-click 이벤트 핸들링//
        const onUpdateBtnClickHandler = async () => {
            if(name.trim() === ''){
                setNameError(true);
                setNameErrorMsg('이름을 입력 바랍니다.');
            }

            const regex = /^[가-힣]*$/;

            if(!regex.test(name.trim()) ){
                setNameError(true);
                setNameErrorMsg('이름을 한글로 입력 바랍니다.');
            }

            await mypageInfoRequest(uuid).then(updateMyInfoResponse);
            // 입력한 데이터를 업데이트 하는 요청 보내기(수정) - put 메서드
        }

        // event-handler: 탈퇴하기 on-click 이벤트 핸들링//
        const onWithdrawBtnClickHandler = () => {
            navigator("/withdraw");
            console.log(`로그인 되지 않은 상태입니다 ~ uuid: ${uuid} role: ${role}`);
            // 현재 비번 입력 -> 맞는지 확인 -> 맞으면 삭제 요청 -> 응답(회원탈퇴하셨습니다.)
        }

        // event-handler: 비밀번호 수정 on-click 이벤트 핸들링//
        const onUpdatePwBtnClickHandler = () => {
            // 비밀번호 수정 페이지로 이동하기
        }


        return(
            <div className="userInfo-card">
                <h1 className="card-title">회원 정보</h1>
                <div className="card-input-box">
                    <InputBox placeholder="닉네임(Nickname)" name="nickname" 
                    type="text" isReadOnly={true} value={nickname} error={false}/>
                    <InputBox placeholder="이메일(E-Mail)" name="email"
                    type="text" isReadOnly={true} value={email} error={false}/>
                    <InputBox placeholder="전화번호" name="phone"
                    type="text" isReadOnly={true} value={phone} error={false}/>
                    <InputBox placeholder="이름" name="name" type="text"
                    value={name} error={isNameError} message={nameErrorMsg} 
                    onChange={onNicknameChangeHandler}/>
                </div>
                <div className="card-btn-box">
                    <ABox label="판매내역" onClick={onDealInfoBtnClickHandler}></ABox>
                    <ABox label="수정하기" onClick={onUpdateBtnClickHandler}/>
                    <ABox label="탈퇴하기" onClick={onWithdrawBtnClickHandler}/>
                    <ABox label="비밀번호 수정" onClick={onUpdatePwBtnClickHandler}/>
                </div>
            </div>
        );
    };

    // component: 마이페이지 - 판매내역 컴포넌트 //
    const DealInfo = () => {
        return(
            <div className="dealInfo-card">
                <h1 className="card-title">판매 내역</h1>
                <ul className="dealInfo-list">
                    <li className="dealInfo-"></li>
                </ul>
            </div>
        );
    };

    return(
        <div className="mypage-wrapper">
            <div className="mypage-container">
                {view === 'user-info' && (<UserInfo/>)}
                {view === 'deal-info' && (<DealInfo/>)}
            </div>
        </div>
    );
}

export default Mypage;