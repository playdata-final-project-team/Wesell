import React, { useCallback,useEffect, useState } from 'react';
import axios from 'axios';
import './Write.css';
import ImageUploader from 'components/ImageUploader/ImageUploader';
import TextArea from 'components/TextArea/TextArea';
import {toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from 'react-router-dom';

function GetCategory() {
  const [category, setCategory] = useState<category[]>([]);

  useEffect(() => {
    axios 
      .get('/deal-service/api/v2/categories')
      .then((response) => {
        console.log(response.data.categories);
        const categoryArray = Object.values(response.data.categories) as category[];
        setCategory(categoryArray);
      })
      .catch((error) => {
        console.error('Error fetching categories:', error);
      });
  }, []);

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

function UploadBoard() {
  const navigate = useNavigate();
  const [ uuid, setUuid] = useState('');
  const [image, setImage] = useState({
    image_file: "",
    preview_URL: "image/default_image.png",
  });
  const categories = GetCategory();
  const [title, setTitle] = useState('');
  const [categoryId, setCategoryId] = useState('');
  const [price, setPrice] = useState('');
  const [detail, setDetail] = useState('');

  const canSubmit = useCallback(() => {
    return image.image_file !== "" && title !== "" && price !== ""
    && detail !== "";
  }, [image, title, price, detail]);

  const handleSubmit = useCallback(async () => {
    try{
      const data1 = {
        uuid: window.sessionStorage.getItem("uuid"),
        categoryId: categoryId,
        title: title,
        price: price,
        detail: detail
      };

      //step 1. 딜 서비스
      const response1 = await axios.post("/deal-service/api/v2/upload", data1);
      const postId = response1.data;

      console.log(postId); 
      //step 2. 이미지 서버
      const formData = new FormData();
      formData.append( 'id',  postId);
      // 파일 추가
      formData.append('file', image.image_file);

      await axios.post("/image-server/api/v2/upload", formData, {
        headers: {
          'Content-Type' : 'multipart/form-data'
        }
      });

      window.alert("😎등록이 완료되었습니다😎");

      navigate('/product/detail/'+ postId);
    } catch (e) {
      // 서버에서 받은 에러 메시지 출력
      toast.error("오류발생! 이모지를 사용하면 오류가 발생할 수 있습니다" + "😭", {
        position: "top-center",
      });
    }

  }, [uuid, categoryId, title, price, detail, image, navigate]);

  return (
    <div className="addBoard-wrapper">
      <div className="addBoard-body">
        <div className="category-select">
            <select value={categoryId} onChange={(e) => setCategoryId(e.target.value)}>
            <option value="" disabled>
              카테고리를 선택하세요.
            </option>
            {categories}
          </select>
        </div>
        <ImageUploader setImage={setImage} preview_URL={image.preview_URL}/>
        <TextArea setTitle={setTitle} setPrice={setPrice} setDetail={setDetail} 
        title={title} price={price} detail={detail}/>
        <div className="submitButton">
          {canSubmit() ? (
            <button className="success-button" onClick={handleSubmit}>
            등록하기
            </button>
            ) : (
            <button
              className="disable-button">
              사진과 내용을 모두 입력하세요😭
            </button>
            )}
        </div>
      </div>
    </div>
  );
}
export default UploadBoard;
