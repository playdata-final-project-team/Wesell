import InputBox from 'components/InputBox';
import ABox from 'components/ABox';
import { ChangeEvent, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './style.css';
import {
  deletePostListRequest,
  myDealListRequest,
  myInfoUpdateRequest,
  mypageInfoRequest,
  saleStatusChangeRequest,
} from 'apis';
import MypageResponseDto from 'apis/response/mypage/mypage.response.dto';
import { ResponseDto } from 'apis/response';
import Withdraw from 'pages/withdraw';
import MyDealListResponseDto from 'apis/response/mypage/my.deal-list.response.dto';
import ListPagenation from 'components/Pagenation';
import PasswordUpdateComponent from 'pages/UserfindbyPwd/PasswordUpdateComponent';
import { DealInfoStatusUpdateRequestDto, MypageUpdateRequestDto } from 'apis/request/mypage';
import { MAIN_PATH } from 'constant';
import ResponseCode from 'constant/response-code.enum';
import { AiFillEdit, AiFillProfile } from 'react-icons/ai';
import {
  BsDoorOpenFill,
  BsFileExcelFill,
  BsFilePersonFill,
  BsFillPencilFill,
} from 'react-icons/bs';
import ReactModal from 'react-modal';
import PageResponseDto from 'apis/response/mypage/my.deal-list-page.response.dto';

// component: 마이페이지 컴포넌트 //
function Mypage() {
  // state: view 상태값 //
  const [view, setView] = useState<'user-info' | 'deal-info'>('user-info');

  const navigator = useNavigate();

  // store: uuid, role 값//
  const uuid = sessionStorage.getItem('uuid');
  const kakoId = sessionStorage.getItem('kakaoId');

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

    // function: navaigation 함수 //
    const navigator = useNavigate();

    // function: 비밀번호 찾기 팝업창 닫기 함수 //
    const closePasswordUpdate = () => {
      setPwUpdatePopupOpen(false);
    };

    // effect: 페이지 리렌더링 시마다 확인 //
    useEffect(() => {
      if (uuid === null) {
        alert('로그인 바랍니다.');
        navigator(MAIN_PATH());
        return;
      }

      // comment: uuid 값 회원 정보 조회하기
      const fetchData = async (uuid: string) => {
        const responseBody: MypageResponseDto | ResponseDto | null = await mypageInfoRequest(uuid);

        if (!responseBody) {
          navigator(MAIN_PATH());
          return;
        }

        const successData = responseBody as MypageResponseDto;

        console.log(successData);
        if (successData.content) {
          setEmail(successData.content.email);

          setName(successData.content.name);

          setNickname(successData.content.nickname);

          setPhone(successData.content.phone);
        }
      };

      fetchData(uuid);
    }, [sessionStorage.getItem('uuid')]);

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

      const { code } = responseBody;
      if (code === ResponseCode.OK) {
        alert('개인정보가 변경되었습니다.');
        return;
      }
    };

    // event-handler: 수정하기 on-click 이벤트 핸들링//
    const onUpdateBtnClickHandler = async () => {
      if (name.trim() === '') {
        setNameError(true);
        setNameErrorMsg('이름을 입력 바랍니다.');
        return;
      }

      const regex = /^[가-힣]*$/;

      if (!regex.test(name.trim())) {
        setNameError(true);
        setNameErrorMsg('이름을 한글로 입력 바랍니다.');
        return;
      }

      const response = await mypageInfoRequest(uuid);
      if (!response) return;

      const successResponse = response as MypageResponseDto;
      if (name === successResponse.content.name) {
        return;
      }

      const requestDto: MypageUpdateRequestDto = { name, nickname, phone };

      await myInfoUpdateRequest(uuid, requestDto).then(updateMyInfoResponse);
    };

    // event-handler: 탈퇴하기 on-click 이벤트 핸들링//
    const onWithdrawBtnClickHandler = () => {
      // 현재 비번 입력 -> 맞는지 확인 -> 맞으면 삭제 요청 -> 응답(회원탈퇴하셨습니다.)
      setDeletePopupOpen(!isDeletePopupOpen);
    };

    // event-handler: 비밀번호 수정 on-click 이벤트 핸들링//
    const onUpdatePwBtnClickHandler = () => {
      // 비밀번호 수정 페이지로 이동하기
      setPwUpdatePopupOpen(true);
    };

    // render: 마이페이지 - 회원정보 컴포넌트 렌더링 //
    return (
      <div className="userInfo-card">
        <h1 className="card-title">회원 정보</h1>
        <div
          className={
            isDeletePopupOpen || isPwUpdatePopupOpen
              ? 'userInfo-card-content dimmed'
              : 'userInfo-card-content'
          }
        >
          <div className="card-input-box">
            <InputBox
              id="mypage-nickname"
              label="Nickname : "
              placeholder="닉네임(Nickname)"
              name="nickname"
              type="text"
              isReadOnly={true}
              value={nickname}
              error={false}
            />
            <InputBox
              id="mypage-email"
              label="Email : "
              placeholder="이메일(E-Mail)"
              name="email"
              type="text"
              isReadOnly={true}
              value={email}
              error={false}
            />
            <InputBox
              id="mypage-phone"
              label="Phone : "
              placeholder="전화번호"
              name="phone"
              type="text"
              isReadOnly={true}
              value={phone}
              error={false}
            />
            <InputBox
              id="mypage-name"
              label="Name : "
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
            <ABox
              label="판매 내역"
              icon={<AiFillProfile size={'25px'} color="rgba(10, 160, 255, 0.7)" />}
              onClick={onDealInfoBtnClickHandler}
            ></ABox>
            <ABox
              label="수정 하기"
              icon={<AiFillEdit size={'25px'} color="rgba(10, 160, 255, 0.7)" />}
              onClick={onUpdateBtnClickHandler}
            />
            <ABox
              label="탈퇴 하기"
              icon={<BsDoorOpenFill size={'25px'} color="rgba(10, 160, 255, 0.7)" />}
              onClick={onWithdrawBtnClickHandler}
            />
            {!kakoId && (
              <ABox
                label="비밀번호 수정"
                icon={<BsFillPencilFill size={'25px'} color="rgba(10, 160, 255, 0.7)" />}
                onClick={onUpdatePwBtnClickHandler}
              />
            )}
          </div>
        </div>
        <ReactModal
          overlayClassName={'modal-overlay'}
          className={'modal-content'}
          isOpen={isDeletePopupOpen}
          onRequestClose={() => {
            setDeletePopupOpen(false);
          }}
          ariaHideApp={false}
          contentLabel={'Pop up Message'}
          shouldCloseOnOverlayClick={false}
        >
          <Withdraw setPopupOpen={setDeletePopupOpen} />
        </ReactModal>
        <ReactModal
          overlayClassName={'modal-overlay'}
          className={'modal-content'}
          isOpen={isPwUpdatePopupOpen}
          onRequestClose={() => {
            setPwUpdatePopupOpen(false);
          }}
          ariaHideApp={false}
          contentLabel={'Pop up Message'}
          shouldCloseOnOverlayClick={false}
        >
          <PasswordUpdateComponent />
        </ReactModal>
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
    // state: 체크된 항목을 담을 배열 //
    const [checkItems, setCheckItems] = useState<number[]>([]);
    // state: 판매 상태 관련 상태값 //
    const [statusChange, setStatusChange] = useState<boolean>(false);
    // state: 삭제 여부 관련 상태값 //
    const [isDeleted, setDeleted] = useState<boolean>(false);

    // function: 체크박스 단일 선택 //
    const handleSingleCheck = (checked: boolean, id: number) => {
      if (checked) {
        setCheckItems((prev) => [...prev, id]);
      } else {
        setCheckItems(checkItems.filter((el) => el !== id));
      }
    };

    // function: 체크박스 전체 선택 //
    const handleAllCheck = (checked: boolean) => {
      if (checked) {
        const idArray: number[] = posts.map((el) => el.id);
        setCheckItems(idArray);
      } else {
        setCheckItems([]);
      }
    };

    // effect: 판매 내역 목록 effect 처리 //
    useEffect(() => {
      async function fetchData(uuid: string | null, page: number) {
        const responseBody: PageResponseDto | ResponseDto | null = await myDealListRequest(
          uuid,
          page,
        );

        if (!responseBody) {
          alert('네트워크 연결 상태를 확인해주세요!');
          return;
        }

        const responseBodyWithDealInfo = responseBody as PageResponseDto;
        const { dtoList, totalElements, size } = responseBodyWithDealInfo;

        setPosts(dtoList);
        SetTotalElements(totalElements);
        setSize(size);
        setStatusChange(false);
        setDeleted(false);
      }

      fetchData(uuid, curPage);
    }, [uuid, curPage, statusChange, isDeleted]);

    // event-handler: 회원정보 on-click 이벤트 핸들링 //
    const onDealInfoBtnClickHandler = () => {
      setView('user-info');
    };

    // event-handler: 삭제하기 button click 처리 //
    const onCheckItemsClickHandler = async () => {
      if (checkItems.length === 0) return;

      const responseBody = await deletePostListRequest(checkItems);

      if (!responseBody) {
        alert('😒 네트워크 연결을 다시 확인해주세요!');
        return;
      }

      const { code } = responseBody;

      if (code === ResponseCode.OK) {
        setDeleted(true);
      }

      setPosts(posts.filter((item) => !checkItems.includes(item.id)));
      setCheckItems([]);
    };

    //event-handler: 판매완료 button click 처리 //
    const onSaleCompleteClickHandler = async (postId: number) => {
      const dto: DealInfoStatusUpdateRequestDto = { uuid, postId };
      await saleStatusChangeRequest(dto);
      setStatusChange(true);
    };

    //event-handler: 수정하기 button click 처리 //
    const onDealInfoUpdateClickHandler = (postId: number) => {
      navigator(`/board/edit/${postId}`);
    };

    // render: 마이페이지 - 판매내역 컴포넌트 렌더링 //
    return (
      <div className="dealInfo-card">
        <h1 className="card-title">판매 내역</h1>
        <div className="dealInfo-card-content">
          <div className="card-list-box">
            <table className="dealInfo-list">
              <thead>
                <tr className="dealInfo-element">
                  <th>
                    {posts && (
                      <div className="dealInfo-list-check-all">
                        <input
                          type="checkbox"
                          name="select-all"
                          onChange={(e) => handleAllCheck(e.target.checked)}
                          checked={checkItems.length === posts.length ? true : false}
                        />
                      </div>
                    )}
                  </th>
                  <th>제목</th>
                  <th>게시일</th>
                  <th>판매상태</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {posts
                  ? posts.map((post, index) => (
                      <tr className="dealInfo-element" key={index}>
                        <td>
                          <input
                            type="checkbox"
                            name={`select-${post.id}`}
                            onChange={(e) => handleSingleCheck(e.target.checked, post.id)}
                            checked={checkItems.includes(post.id) ? true : false}
                          />
                        </td>
                        <td>{post.title}</td>
                        <td>{post.createdAt}</td>
                        <td>{post.saleStatus === 'IN_PROGRESS' ? '판매 중' : '판매 완료'}</td>
                        <td>
                          <div className="dealInfo-element-btn">
                            {post.saleStatus === 'IN_PROGRESS' ? (
                              <>
                                <button
                                  type="button"
                                  onClick={() => {
                                    onSaleCompleteClickHandler(post.id);
                                  }}
                                >
                                  판매 완료
                                </button>
                                <button
                                  onClick={() => {
                                    onDealInfoUpdateClickHandler(post.id);
                                  }}
                                  type="button"
                                >
                                  수정
                                </button>
                              </>
                            ) : (
                              <button
                                type="button"
                                onClick={() => {
                                  onSaleCompleteClickHandler(post.id);
                                }}
                              >
                                판매중
                              </button>
                            )}
                          </div>
                        </td>
                      </tr>
                    ))
                  : null}
              </tbody>
            </table>
            {!posts && <p>😒 등록 하신 판매글이 없습니다!</p>}
          </div>
          <div className="card-btn-box">
            <ABox
              label="회원 정보"
              icon={<BsFilePersonFill size={'25px'} color="rgba(10, 160, 255, 0.7)" />}
              onClick={onDealInfoBtnClickHandler}
            ></ABox>
            <ABox
              label="삭제하기"
              icon={<BsFileExcelFill size={'25px'} color="#f46b6b" />}
              onClick={onCheckItemsClickHandler}
            />
          </div>
        </div>
        {posts && (
          <ListPagenation
            limit={size}
            page={curPage}
            setPage={setCurPage}
            blockNum={blockNum}
            counts={totalElements}
            setBlockNum={setBlockNum}
          />
        )}
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
