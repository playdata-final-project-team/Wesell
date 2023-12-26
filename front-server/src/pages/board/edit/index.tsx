import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
  
const EditPostPage = () => {
  const navigate = useNavigate();
  const { postId } = useParams();
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

   const {category, title, price, detail, link, imageUrl} = post; //비구조화 할당

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

    <div>
        <span>제목</span>
        <input type="text" name="title" value={title} onChange={onChange} />
      </div>
      <div>
        <span>카테고리</span>
        <input type="text" name="category" value={category} onChange={onChange} />
      </div>
      <br />
      <div>
        <span>가격</span>
        <input type="text" name="price" value={price} onChange={onChange} />
      </div>
      <br />
      <div>
        <span>설명</span>
        <input type="text" name="detail" value={detail} onChange={onChange} />
      </div>
      <div>
        <span>톡방링크</span>
        <input type="text" name="link" value={link} onChange={onChange} />
      </div>
      <br />
      <div>
        <button onClick={updateBoard}>수정</button>
        <button onClick={backToDetail}>취소</button>
      </div>
    </>
  );
};
export default EditPostPage;
