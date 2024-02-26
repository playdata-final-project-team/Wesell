import { Dispatch, SetStateAction } from 'react';
import './style.css';

const ListPagination = ({
  limit,
  page,
  setPage,
  blockNum,
  setBlockNum,
  counts,
}: {
  limit: number; // comment : 한 페이지 항목 최대 갯수 //
  page: number; // comment : 현재 페이지 //
  setPage: Dispatch<SetStateAction<number>>;
  blockNum: number; // comment : 페이지 갯수 - 현재 페이지 앞 뒤로 blockNum 만큼만 보여준다//
  setBlockNum: Dispatch<SetStateAction<number>>;
  counts: number; // comment : 전체 항목 갯수 //
}): JSX.Element => {
  const createArr = (n: number) => {
    const iArr: number[] = new Array(n);
    for (let i = 0; i < n; i++) iArr[i] = i + 1;
    return iArr;
  };

  const pageLimit = 10;

  const totalPage: number = Math.ceil(counts / limit);

  const blockArea = Number(blockNum * pageLimit);

  const nArr = createArr(totalPage);
  // eslint-disable-next-line prefer-const
  let pArr = nArr.slice(blockArea, Number(pageLimit) + blockArea);

  // 첫페이지로 이동
  const firstPage = () => {
    setPage(1);
    setBlockNum(0);
  };

  // 마지막 페이지로 이동
  const lastPage = () => {
    setPage(totalPage);
    setBlockNum(Math.ceil(totalPage / pageLimit) - 1);
  };

  // 이전 페이지로 이동
  const prevPage = () => {
    if (page <= 1) {
      return;
    }

    if (page - 1 <= pageLimit + blockNum && blockNum > 1) {
      setBlockNum((n: number) => n - 1);
    }

    setPage((n: number) => n - 1);
  };

  // 다음 페이지로 이동
  const nextPage = () => {
    if (page >= totalPage) {
      return;
    }

    if (pageLimit * (blockNum + 1) < page + 1) {
      setBlockNum((n: number) => n + 1);
    }

    setPage((n: number) => n + 1);
  };

  return (
    <div className="listPagenationWrapper">
      <button
        title="처음으로"
        className="moveToFirstPage"
        onClick={() => {
          firstPage();
        }}
      >
        &lt;&lt;
      </button>
      <button
        title="이전"
        className="moveToPrevPage"
        onClick={() => {
          prevPage();
        }}
        disabled={page === 1}
      >
        &lt;
      </button>
      <div className="pageBtnWrapper">
        {pArr.map((n: number) => (
          <button
            className={`pageBtn ${page === n ? 'current' : ''}`}
            key={n}
            onClick={() => {
              setPage(n);
            }}
            aria-current={page === n ? 'page' : undefined}
          >
            {n}
          </button>
        ))}
      </div>
      <button
        title="다음"
        className="moveToNextPage"
        onClick={() => {
          nextPage();
        }}
        disabled={page === totalPage}
      >
        &gt;
      </button>
      <button
        title="마지막으로"
        className="moveToLastPage"
        onClick={() => {
          lastPage();
        }}
      >
        &gt;&gt;
      </button>
    </div>
  );
};

export default ListPagination;
