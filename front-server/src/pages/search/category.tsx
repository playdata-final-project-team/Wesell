import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';

interface PostJson {
  "postId":number;
  "imageUrl":string;
  "title":string;
  "price":number;
}

const SearchByCategory = () => {
  const [postJson, setPostJson] = useState<PostJson[]>();
  const navigate = useNavigate();
  const {categoryId} = useParams();
  const [searchValue, setSearchValue] = useState('');

  useEffect( () => {
    const POST_LIST_ENDPOINT = `/deal-service/api/v1/main/category?=${categoryId}?page=0`; // 페이지 파라미터를 직접 URL에 추가

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
  }, []);

  const handleCategoryButtonClick = (categoryId : number) => {
    navigate(`/main/categoryId=${categoryId}`);
  };

  const handleSearch = () => {
    if (searchValue.trim() !== '') {
      navigate(`/main/search?title=${encodeURIComponent(searchValue)}`);
    }
  };
  
  return (
    <>
    <div className="searching-box">
    <input type="text"
          placeholder="제목을 검색하세요"
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)} />
          <button onClick={handleSearch}>
            <img src = "./assets/image.png" />
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
      {postJson?.map(post => (
        <Link key={post.postId} to={`/board/detail/${post.postId}`}>
          <img src={post.imageUrl} />
          <p>{post.title}</p>
          <p>{post.price}</p>
        </Link>
      ))}
    </div>
      </>
    );
}

export default SearchByCategory;