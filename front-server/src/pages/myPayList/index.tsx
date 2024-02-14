import ABox from 'components/ABox';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ResponseDto } from 'apis/response';
import ListPagenation from 'components/Pagination';
import ResponseCode from 'constant/response-code.enum';
import { BsFileExcelFill } from 'react-icons/bs';
import PayPageResponseDto from 'apis/response/myPayPage/my.pay-list-page';
import {
  deletePayListRequest,
  shippingStatusChangeRequest,
  myPayListRequest,
} from 'apis/response/myPayPage';

// component: 마이페이지 컴포넌트 //
function MyPayList() {
  // state: view 상태값 //
  const [view, setView] = useState<'deal-info'>();
  const navigator = useNavigate();
  // store: uuid, role 값//
  // const uuid = sessionStorage.getItem('uuid');
  const uuid = 'wid';

  // component: 마이페이지 - 구매내역 컴포넌트 //
  const PayInfo = () => {
    // state: 판매 내역 한페이지 게시글 목록 상태값 //
    const [posts, setPosts] = useState<PayPageResponseDto[]>([]);
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

    // function: 체크박스
    const handleSingleCheck = (checked: boolean, id: number) => {
      if (checked) {
        setCheckItems((prev) => [...prev, id]);
      } else {
        setCheckItems(checkItems.filter((el) => el !== id));
      }
    };
    const handleAllCheck = (checked: boolean) => {
      if (checked) {
        const idArray: number[] = posts.map((el) => el.payId);
        setCheckItems(idArray);
      } else {
        setCheckItems([]);
      }
    };

    // effect: 구매 내역 목록
    useEffect(() => {
      async function fetchData(uuid: string | null, page: number) {
        const responseBody: PayPageResponseDto | ResponseDto | null = await myPayListRequest(
          uuid,
          page,
        );

        if (!responseBody) {
          alert('네트워크 연결 상태를 확인해주세요!');
          return;
        }

        SetTotalElements(totalElements);
        setSize(size);
        setStatusChange(false);
        setDeleted(false);
      }

      fetchData(uuid, curPage);
    }, [uuid, curPage, statusChange, isDeleted]);

    // event-handler: 삭제하기 button click 처리 //
    const onCheckItemsClickHandler = async () => {
      if (checkItems.length === 0) return;

      const responseBody = await deletePayListRequest(checkItems);

      if (!responseBody) {
        alert('😒 네트워크 연결을 다시 확인해주세요!');
        return;
      }

      const { code } = responseBody;

      if (code === ResponseCode.OK) {
        setDeleted(true);
      }

      setPosts(posts.filter((item) => !checkItems.includes(item.payId)));
      setCheckItems([]);
    };

    //event-handler: 구매 확정 button click 처리 //
    const shippingCompleteClickHandler = async (deliveryId: number) => {
      await shippingStatusChangeRequest(deliveryId);
      setStatusChange(true);
    };

    //event-handler: 수정하기 button click 처리 //
    const onShippingAddressUpdateClickHandler = (deliveryId: number) => {
      navigator(`/delivery/edit/${deliveryId}`);
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
                  <th>구매일</th>
                  <th>배송현황</th>
                  <th></th>
                </tr>
              </thead>
              <tbody>
                {posts
                  ? posts.map((post, index) => (
                      <tr className="payInfo-element" key={index}>
                        <td>
                          <input
                            type="checkbox"
                            name={`select-${post.payId}`}
                            onChange={(e) => handleSingleCheck(e.target.checked, post.payId)}
                            checked={checkItems.includes(post.payId) ? true : false}
                          />
                        </td>
                        <td>{post.title}</td>
                        <td>{post.date}</td>
                        <td>{post.status}</td>
                        <td>
                          <div className="payInfo-element-btn">
                            {post.status === '배송 완료' && (
                              <>
                                <button
                                  type="button"
                                  onClick={() => {
                                    shippingCompleteClickHandler(post.deliveryId);
                                  }}
                                >
                                  구매 확정
                                </button>
                              </>
                            )}
                            {post.status === '배송 준비 중' && (
                              <button
                                onClick={() => {
                                  onShippingAddressUpdateClickHandler(post.deliveryId);
                                }}
                                type="button"
                              >
                                배송지 수정
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
      <div className="mypage-container">{view === 'deal-info' && <PayInfo />}</div>
    </div>
  );
}

export default MyPayList;
