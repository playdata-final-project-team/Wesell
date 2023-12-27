import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { FaSearch } from "react-icons/fa";
import ListPagenation from 'components/Pagenation';

interface PostJson {
  "postId":number;
  "imageUrl":string;
  "title":string;
  "price":number;
}

const SearchByTitle = () => {
  const [postJson, setPostJson] = useState<PostJson[]>();
  const navigate = useNavigate();
  const {title} = useParams();
  const [searchValue, setSearchValue] = useState('');
      // state: 현재 페이지 상태값 //
      const [curPage, setCurPage] = useState<number>(1);
      // state: 한 페이지 보일 수 있는 항목 갯수 상태값 //
      const [size, setSize] = useState<number>(8);
      // state: 한 페이지에 보여 줄 페이지네이션 갯수 상태값 //
      const [blockNum, setBlockNum] = useState<number>(0);
      // state: 전체 항목 갯수 상태값 //
      const [totalElements, SetTotalElements] = useState<number>(0);

  useEffect( () => {
    const POST_LIST_ENDPOINT = `/deal-service/api/v1/main/title?title=${title}&page=0`; // /deal-service/api/v1/post?id=${postId}

    fetch(POST_LIST_ENDPOINT, {
      method: "GET"
    })
      .then(res => res.json()) // res.json()을 호출하여 JSON 데이터를 가져옴
      .then(data => {
        console.log(data); // 가져온 데이터를 콘솔에 출력하거나 원하는 작업 수행
        setPostJson(data['content']);
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }, [title]);

  const handleCategoryButtonClick = (categoryId : number) => {
    navigate(`/main/category/`+categoryId );
  };

  const handleSearch = () => {
    if (searchValue.trim() !== '') {
      navigate(`/main/title/`+ encodeURIComponent(searchValue));
    }
  };
  
  return (
    <>
    <div className="searching-box">
    <input type="text"
          placeholder=" Search..."
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)} />
          <button onClick={handleSearch}>
          <FaSearch color="#00A8CC" />
          </button>
    </div>
    <div className="categoryList">
    <button onClick={() =>handleCategoryButtonClick(1)}>의류잡화</button>
    <button onClick={() =>handleCategoryButtonClick(2)}>식기</button>
    <button onClick={() =>handleCategoryButtonClick(3)}>전자제품</button>
    <button onClick={() =>handleCategoryButtonClick(4)}>헬스</button>
    <button onClick={() =>handleCategoryButtonClick(5)}>기타</button>
    </div>
    <div className = "postList">
      {!postJson && <p>😒 검색 결과가 없습니다.</p>}
      {postJson?.map(post => (
        <Link key={post.postId} to={`/board/detail/${post.postId}`}>
          <div className="postItem">
          <div className="board-body-img">
            <img src={post.imageUrl} />
          </div>
          <div className="board-body-text">
          <div className="text-title">
            <p>{post.title}</p>
          </div>
          <div className="text-price">
            <p>{post.price}</p>
          </div>
          </div>
          </div>
        </Link>
      ))}
    </div>
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
      </>
    );
}

export default SearchByTitle;