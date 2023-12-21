import InputBox from 'components/InputBox';
import ABox from 'components/aBox';
import { ChangeEvent, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './style.css';
import useStore from 'stores';
import { myDealListRequest, mypageInfoRequest } from 'apis';
import MypageResponseDto from 'apis/response/mypage/mypage.response.dto';
import { ResponseDto } from 'apis/response';
import Withdraw from 'pages/withdraw';
import MyDealListWithPageResponseDto from 'apis/response/mypage/my.deal-list-page.response.dto';
import MyDealListResponseDto from 'apis/response/mypage/my.deal-list.response.dto';
import ListPagenation from 'components/Pagenation';
import PasswordUpdateComponent from 'pages/UserfindbyPwd/PasswordUpdateComponent';

// component: 마이페이지 컴포넌트 //
function Mypage() {
  // state: view 상태값 //
  const [view, setView] = useState<'user-info' | 'deal-info'>('user-info');

  // component: 마이페이지 - 회원정보 컴포넌트 //
  const UserInfo = () => {
    // state: 탈퇴하기 팝업창 상태값 //
    const [isDeletePopupOpen, setDeletePopupOpen] = useState<boolean>(false);
    // state: 비밀번호 수정 팝업창 상태값 //
    const [isPwUpdatePopupOpen, setPwUpdatePopupOpen] = useState<boolean>(false);
    // state: 닉네임 상태값 //
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

    // function: navaigation 함수 //
    const navigator = useNavigate();

    // effect: 페이지 리렌더링 시마다 확인 //
    useEffect(() => {
      if (uuid === null) {
        // alert("로그인 바랍니다.");
        // navigator("/");
        return;
      }

      // comment: uuid 값 회원 정보 조회하기
      const feignData = async (uuid: string) => {
        const responseBody: MypageResponseDto | null = await mypageInfoRequest(uuid);

        if (!responseBody) {
          alert('네트워크 연결 상태를 확인해주세요!');
          return;
        }

        if ('email' in responseBody) {
          setEmail(responseBody.email);
        }

        if ('name' in responseBody) {
          setName(responseBody.name);
        }

        if ('nickname' in responseBody) {
          setNickname(responseBody.nickname);
        }

        if ('phone' in responseBody) {
          setPhone(responseBody.phone);
        }
      };

      feignData(uuid);
    }, []);

    // event-handler: 이름 on-change 이벤트 핸들링 //
    const onNicknameChangeHandler = (event: ChangeEvent<HTMLInputElement>) => {
      setNameError(false);
      setNameErrorMsg('');
      const { value } = event.target;
      setName(value);
    };

    // event-handler: 판매내역 on-click 이벤트 핸들링 //
    const onDealInfoBtnClickHandler = () => {
      setView('deal-info');
    };

    // function: 회원정보 수정 후 처리 함수 //
    const updateMyInfoResponse = (responseBody: ResponseDto | null) => {
      if (!responseBody) {
        alert('네트워크 연결 상태를 확인해주세요!');
        return;
      }
    };

    // event-handler: 수정하기 on-click 이벤트 핸들링//
    const onUpdateBtnClickHandler = async () => {
      if (name.trim() === '') {
        setNameError(true);
        setNameErrorMsg('이름을 입력 바랍니다.');
      }

      const regex = /^[가-힣]*$/;

      if (!regex.test(name.trim())) {
        setNameError(true);
        setNameErrorMsg('이름을 한글로 입력 바랍니다.');
      }

      await mypageInfoRequest(uuid).then(updateMyInfoResponse);
      // 입력한 데이터를 업데이트 하는 요청 보내기(수정) - put 메서드
    };

    // event-handler: 탈퇴하기 on-click 이벤트 핸들링//
    const onWithdrawBtnClickHandler = () => {
      // 현재 비번 입력 -> 맞는지 확인 -> 맞으면 삭제 요청 -> 응답(회원탈퇴하셨습니다.)
      setDeletePopupOpen(!isDeletePopupOpen);
    };

    // event-handler: 비밀번호 수정 on-click 이벤트 핸들링//
    const onUpdatePwBtnClickHandler = () => {
      // 비밀번호 수정 페이지로 이동하기
      setPwUpdatePopupOpen(!isPwUpdatePopupOpen);
    };

    // render: 마이페이지 - 회원정보 컴포넌트 렌더링 //
    return (
      <div className="userInfo-card">
        <div
          className={
            isDeletePopupOpen || isPwUpdatePopupOpen
              ? 'userInfo-card-content dimmed'
              : 'userInfo-card-content'
          }
        >
          <h1 className="card-title">회원 정보</h1>
          <div className="card-input-box">
            <InputBox
              placeholder="닉네임(Nickname)"
              name="nickname"
              type="text"
              isReadOnly={true}
              value={nickname}
              error={false}
            />
            <InputBox
              placeholder="이메일(E-Mail)"
              name="email"
              type="text"
              isReadOnly={true}
              value={email}
              error={false}
            />
            <InputBox
              placeholder="전화번호"
              name="phone"
              type="text"
              isReadOnly={true}
              value={phone}
              error={false}
            />
            <InputBox
              placeholder="이름"
              name="name"
              type="text"
              value={name}
              error={isNameError}
              message={nameErrorMsg}
              onChange={onNicknameChangeHandler}
            />
          </div>
          <div className="card-btn-box">
            <ABox label="판매 내역" onClick={onDealInfoBtnClickHandler}></ABox>
            <ABox label="수정 하기" onClick={onUpdateBtnClickHandler} />
            <ABox label="탈퇴 하기" onClick={onWithdrawBtnClickHandler} />
            <ABox label="비밀번호 수정" onClick={onUpdatePwBtnClickHandler} />
          </div>
        </div>
        {isDeletePopupOpen && <Withdraw setPopupOpen={setDeletePopupOpen} />}
        {isPwUpdatePopupOpen && <PasswordUpdateComponent />}
      </div>
    );
  };

  // component: 마이페이지 - 판매내역 컴포넌트 //
  const DealInfo = () => {
    // state: 판매 내역 한페이지 게시글 목록 상태값 //
    const [posts, setPosts] = useState<MyDealListResponseDto[]>([]);
    // state: 현재 페이지 상태값 //
    const [curPage, setCurPage] = useState<number>(1);
    // state: 한 페이지 보일 수 있는 항목 갯수 상태값 //
    const [size, setSize] = useState<number>(10);
    // state: 한 페이지에 보여 줄 페이지네이션 갯수 상태값 //
    const [blockNum, setBlockNum] = useState<number>(0);
    // state: 전체 항목 갯수 상태값 //
    const [totalElements, SetTotalElements] = useState<number>(0);

    // context: uuid, role 값//
    const { uuid } = useStore((state) => state);

    // effect: 판매 내역 목록 effect 처리 //
    useEffect(() => {
      async function fetchData(uuid: string | null, page: number) {
        const responseBody: MyDealListWithPageResponseDto | ResponseDto | null =
          await myDealListRequest(uuid, page);

        console.log(responseBody);

        if (!responseBody) {
          alert('네트워크 연결 상태를 확인해주세요!');
          return;
        }

        const responseBodyWithDealInfo = responseBody as MyDealListWithPageResponseDto;
        const { empty, content, totalElements, size } = responseBodyWithDealInfo;

        if (empty) {
          return <p>판매 게시글이 없습니다.</p>;
        }

        setPosts(content);
        SetTotalElements(totalElements);
        setSize(size);
      }

      fetchData(uuid, curPage);
    }, [uuid, curPage]);

    // event-handler: 회원정보 on-click 이벤트 핸들링 //
    const onDealInfoBtnClickHandler = () => {
      setView('user-info');
    };

    // render: 마이페이지 - 판매내역 컴포넌트 렌더링 //
    return (
      <div className="dealInfo-card">
        <h1 className="card-title">판매 내역</h1>
        <div className="card-list-box">
          <table className="dealInfo-list">
            <thead>
              <tr className="dealInfo-element">
                <th></th>
                <th>제목</th>
                <th>게시일</th>
                <th>판매 중/ 판매완료</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {posts.map((post, index) => (
                <tr className="dealInfo-element" key={index}>
                  <td></td>
                  <td>{post.title}</td>
                  <td>{post.createdAt}</td>
                  <td>{post.status}</td>
                  <div className="dealInfo-element-btn">
                    <button type="button">수정</button>
                  </div>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
        <div className="card-btn-box">
          <ABox label="회원 정보" onClick={onDealInfoBtnClickHandler}></ABox>
          <ListPagenation
            limit={size}
            page={curPage}
            setPage={setCurPage}
            blockNum={blockNum}
            counts={totalElements}
            setBlockNum={setBlockNum}
          />
        </div>
      </div>
    );
  };

  // render: 마이페이지 컴포넌트 렌더링 //
  return (
    <div className="mypage-wrapper">
      <div className="mypage-container">
        {view === 'user-info' && <UserInfo />}
        {view === 'deal-info' && <DealInfo />}
      </div>
    </div>
  );
}

export default Mypage;
