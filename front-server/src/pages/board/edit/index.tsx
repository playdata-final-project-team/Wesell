import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './index.css';

function GetCategory() {
  const [category, setCategory] = useState<category[]>([]);

  useEffect(() => {
    axios //8000/deal-service/api
      .get('/deal-service/api/v1/categories')
      .then((response) => {
        console.log(response.data.categories);
        const categoryArray = Object.values(response.data.categories) as category[];
        setCategory(categoryArray);
      })
      .catch((error) => {
        console.error('Error fetching categories:', error);
      });
  }, []);

  console.log(category);

  type category = {
    id: number;
    value: string;
  };

  const categories = category.map((item: category, i) => (
    <option key={i} value={item.id}>
      {item.value}
    </option>
  ));

  return categories;
}

const EditPostPage = () => {
  const navigate = useNavigate();
  const { postId } = useParams();
  const categories = GetCategory();
  const [category, setCategory] = useState('');
  const [ post, setPost] = useState({
    postId: 0,
    category: '',
    title: '',
    createdAt: '',
    price: '',
    detail: '', 
    link: '',
    nickname: '',
    imageUrl: '',
  });
  
   const {title, price, detail, link} = post; //비구조화 할당

   const onChange = (event: { target: { value: any; name: any; }; }) => {
    const {value, name} = event.target; //event.target에서 name과 value만 가져오기  
    setPost({
      ...post,
      [name]:value,
    });
   };
  
  const getBoard = async () => {
    const response = await (await axios.get(`/deal-service/api/v1/post?id=${postId}`)).data;
    setPost(response);
  };
  

  const updateBoard = async () => {
    await axios.put(`/deal-service/api/v1/edit?id=${postId}`, post).then((res) => {
      alert('수정되었습니다.');
      navigate('/board/detail/'+postId);
    });
  };

  const backToDetail = () => {
    navigate('/board/detail/'+postId);
  };

  useEffect(() => {
    getBoard();
  }, []);
  return (
    <>
    <div className="board-wrapper">
      <div className="board-body" >
        <div className="board-image">
          <img src ={post.imageUrl} />
        </div>
        <div className="borad-content">
          <input type="text" name="title" placeholder="제목" value={title} onChange={onChange} />
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}>
            <option value="" disabled>카테고리를 선택하세요.</option>
            {categories}
          </select>
          <input type="text" name="category" value={category} onChange={onChange} />
            <input type="text" name="price" placeholder="가격" value={price} onChange={onChange} />
            <input type="text" name="detail" placeholder="상세 설명" value={detail} onChange={onChange} />
            <input type="text" name="link" placeholder="오픈 채팅방 링크" value={link} onChange={onChange} />
        </div>
      </div>
      <div className="button-wrapper">
        <button className="update-button" onClick={updateBoard}>수정</button>
        <button className="cancel-button" onClick={backToDetail}>취소</button>
      </div>
    </div>
    </>
  );
};
export default EditPostPage;
