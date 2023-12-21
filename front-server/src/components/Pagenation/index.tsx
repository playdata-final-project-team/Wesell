import { Dispatch, SetStateAction } from "react";
import './style.css';

const ListPagenation = ({
    limit,
    page,
    setPage,
    blockNum,
    setBlockNum,
    counts
}:{
    limit: number;
    page:number;
    setPage: Dispatch<SetStateAction<number>>;
    blockNum: number;
    setBlockNum: Dispatch<SetStateAction<number>>;
    counts: number;
}): JSX.Element =>{
    const createArr = (n: number) => {
        const iArr: number[] = new Array(n);
        for(let i=0; i<n; i++) iArr[i] = i+1;
        return iArr;
    };
    const pageLimit = 10;

    const totalPage: number = Math.ceil(counts/limit);

    const blockArea = Number(blockNum * pageLimit);

    const nArr = createArr(totalPage);
    // eslint-disable-next-line prefer-const
    let pArr = nArr?.slice(blockArea, Number(pageLimit) + blockArea);

    const firstPage = () => {
        setPage(1);
        setBlockNum(0);
    };

    const lastPage = () => {
        setPage(totalPage);
        setBlockNum(Math.ceil(totalPage/pageLimit) -1);
    };

    const prevPage = () => {
        if(page <= 1) {
            return;
        }

        if(page -1 <= pageLimit + blockNum){
            setBlockNum((n: number)=>n-1);
        }
        setPage((n:number) => n-1);
    };

    const nextPage = () => {
        if(page >= totalPage){
            return;
        }// page가 마지막 페이지보다 크거나 같은 경우 막기
        if(pageLimit * (blockNum+1) < page+1){
            setBlockNum((n:number)=> n+1);
        }
        setPage((n:number)=>n+1);
    };

    return(
        <div className="listPagenationWrapper">
            <button className="moveToFirstPage"
            onClick={() => {
                firstPage();
            }}>
                &lt;&lt;
            </button>
            <button className="moveToPrevPage" 
            onClick={()=>{
                prevPage();
            }} disabled={page === 1}>
                &lt;
            </button>
            <div className="pageBtnWrapper">
                {pArr.map((n: number)=>(
                    <button className="pageBtn" key={n}
                    onClick={()=>{
                        setPage(n);
                    }}
                    aria-current={page === n ? 'page': undefined}
                    >
                        {n}
                    </button>
                ))}
            </div>
            <button className="moveToNextPage"
            onClick={()=>{
                nextPage();
            }} disabled={page === totalPage}>
                &gt;
            </button>
            <button className="moveToLastPage"
            onClick={()=>{
                lastPage();
            }}>
                &gt;&gt;
            </button>
        </div>
    );
};

export default ListPagenation;