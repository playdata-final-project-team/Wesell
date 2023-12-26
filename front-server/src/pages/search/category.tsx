import { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import './style.css';
import { FaSearch } from "react-icons/fa";

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
    const POST_LIST_ENDPOINT = `/deal-service/api/v1/main/category?category=${categoryId}&page=0`; // /deal-service/api/v1/post?id=${postId}

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
  }, [categoryId]);

  const handleCategoryButtonClick = (categoryId : number) => {
    navigate(`/main/category/`+categoryId ); //'/board/edit/' + postId
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
          placeholder="Search..."
          value={searchValue}
          onChange={(e) => setSearchValue(e.target.value)} />
          <button className='search-icon-button' onClick={handleSearch}>
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
      {postJson?.map(post => (
        <Link className = "one-board" key={post.postId} to={`/board/detail/${post.postId}`}>
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