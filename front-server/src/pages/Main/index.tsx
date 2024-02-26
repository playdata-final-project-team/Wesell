import { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FaSearch } from 'react-icons/fa';
import { Card } from 'components/card/Card';
import ListPagenation from 'components/Pagination';
import './style.css';

interface PostJson {
  postId: number;
  imageUrl: string;
  title: string;
  price: number;
}

const Main = () => {
  const [postJson, setPostJson] = useState<PostJson[]>();
  const navigate = useNavigate();
  const [searchValue, setSearchValue] = useState('');

  // state: 현재 페이지 상태값 //
  const [curPage, setCurPage] = useState<number>(1);
  // state: 한 페이지 보일 수 있는 항목 갯수 상태값 //
  const [size, setSize] = useState<number>(8);
  // state: 한 페이지에 보여 줄 페이지네이션 갯수 상태값 //
  const [blockNum, setBlockNum] = useState<number>(0);
  // state: 전체 항목 갯수 상태값 //
  const [totalElements, setTotalElements] = useState<number>(0);

  useEffect(() => {
    const POST_LIST_ENDPOINT = `/deal-service/api/v2/main?page=${curPage}`; // 페이지 파라미터를 직접 URL에 추가

    fetch(POST_LIST_ENDPOINT, {
      method: 'GET',
    })
      .then((res) => res.json()) // res.json()을 호출하여 JSON 데이터를 가져옴
      .then((data) => {
        console.log(data); // 가져온 데이터를 콘솔에 출력하거나 원하는 작업 수행
        setPostJson(data['dtoList']);
        setTotalElements(data['totalElements']);
        setSize(data['size']);
      })
      .catch((error) => {
        console.error('Error fetching data:', error);
      });
  }, [curPage]);

  const handleCategoryButtonClick = (categoryId: number) => {
    navigate('/category/' + categoryId);
  };

  const handleSearch = () => {
    if (searchValue.trim() !== '') {
      navigate('/title/' + encodeURIComponent(searchValue));
    }
  };

  return (
    <div className="boardList-container">
      <div className="searching-box">
        <input
          type="text"
          placeholder=" Search..."
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)}
        />
        <button onClick={handleSearch}>
          <FaSearch color="#00A8CC" />
        </button>
      </div>
      <div className="categoryList">
        <button onClick={() => handleCategoryButtonClick(1)}>의류잡화</button>
        <button onClick={() => handleCategoryButtonClick(2)}>식기</button>
        <button onClick={() => handleCategoryButtonClick(3)}>전자제품</button>
        <button onClick={() => handleCategoryButtonClick(4)}>헬스</button>
        <button onClick={() => handleCategoryButtonClick(5)}>기타</button>
      </div>
      <div className="boardList-wrapper">
        <div className="boardList-body">
          {postJson?.map((item) => (
            <Card
              key={item.postId}
              board_id={item.postId}
              title={item.title}
              img_url={item.imageUrl}
              price={item.price}
            />
          ))}
        </div>
        <div className="boardList-footer">
          {postJson && (
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
      </div>
    </div>
  );
};

export default Main;
