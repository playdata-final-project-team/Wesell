import React, { useCallback, useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import axios from 'axios';
import './index.css';
import TextArea from 'components/TextArea/TextArea';
import { toast } from 'react-toastify';

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

const [title, setTitle] = useState("");
const [price, setPrice] = useState(0);
const [detail, setDetail] = useState("");
const [link, setLink] = useState("");
const [image, setImage] = useState("");

  const getBoard = async () => {
    const response = await (await axios.get(`/deal-service/api/v1/post?id=${postId}`)).data;
    setTitle(response.title);
    setPrice(response.price);
    setDetail(response.detail);
    setLink(response.link);
    setImage(response.imageUrl);
  };

  const canSubmit = useCallback(() => {
    return price !== 0 && title !== "" && category !==""
            && detail !=="" && link !=="";
  }, [price, title, category, detail, link]);

  const updateBoard =  useCallback(async () => {
    try{
      const data = {
        postId: postId,
        categoryId: category,
        title: title,
        price: price,
        detail: detail,
        link: link
      }
      await axios.put(`/deal-service/api/v1/edit?id=${postId}`, data)
    .then(() => {
    alert('수정되었습니다.');
    navigate('/board/detail/'+postId)});
    } catch(e) {
      toast.error("모든 정보를 명확히 입력해주세요.")
    }
    
},[canSubmit]);

  const backToDetail = () => {
    navigate('/board/detail/'+postId);
  };

  useEffect(() => {
    getBoard();
  }, []);
  
  return (
    <>
    
      <div className="category-select">
        <select value={category}
          onChange={(e) => setCategory(e.target.value)}>
           <option value="" disabled>카테고리를 선택하세요.</option>
            {categories}
        </select>
      </div>
      <div className="board-wrapper">
        <div className="board-body">
          <div className="board-image">
            <img src ={image} />
          </div>
          <div className="board-content">
            <TextArea setTitle={setTitle} setPrice={setPrice} setDetail={setDetail} setLink={setLink} 
            title={title} price={price} detail={detail} link={link} />
          </div>
        </div>
      </div>
      <div className="submitButton">
        {canSubmit() ? (
          <button className="success-button" onClick={updateBoard}>수정 완료</button>
        ) : (
          <button className="disable-button">제목과 내용을 모두 입력하세요😭</button>
        )}
        </div>
        <button className="cancel-button" onClick={backToDetail}>취소</button>
    </>
  );
};
export default EditPostPage;
