import React, { useCallback,useEffect, useState } from 'react';
import axios from 'axios';
import './Write.css';
import ImageUploader from 'components/ImageUploader/ImageUploader';
import TextArea from 'components/TextArea/TextArea';
import {Button} from "@mui/material";
import {toast} from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useNavigate } from 'react-router-dom';

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

function UploadBoard() {
  const navigate = useNavigate();
  const [ uuid, setUuid] = useState('www');
  const [image, setImage] = useState({
    image_file: "",
    preview_URL: "image/default_image.png",
  });
  const categories = GetCategory();
  const [title, setTitle] = useState('');
  const [categoryId, setCategoryId] = useState('');
  const [price, setPrice] = useState('');
  const [detail, setDetail] = useState('');
  const [link, setLink] = useState('');

  const canSubmit = useCallback(() => {
    return image.image_file !== "" && title !== "";
  }, [image, title, categoryId,price,detail,link]);


  // useEffect(() => {
  //   const uuid = window.sessionStorage.getItem("uuid");
  //   if (uuid) {
  //     setUuid(uuid);
  //   }
  // }, []);

  const handleSubmit = useCallback(async () => {
    try{
      const formData = new FormData();

      // 필드 추가
    formData.append("requestDto", JSON.stringify({
      uuid,
      categoryId,
      title,
      price,
      link,
      detail,
    }));
    // 파일 추가
    formData.append("file", image.image_file);

      const response = await axios.post("/deal-service/api/v1/upload", formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });

      window.alert("😎등록이 완료되었습니다😎");

      const {postId} = response.data;
      navigate('/board/detail/'+postId);
    } catch (e) {
      // 서버에서 받은 에러 메시지 출력
      toast.error("오류발생! 이모지를 사용하면 오류가 발생할 수 있습니다" + "😭", {
        position: "top-center",
      });
    }

  }, [uuid, categoryId, title, price, link, detail, image, navigate]);

  return (
    <div className="addBoard-wrapper">
      <div className="submitButton">
        {canSubmit() ? (
          <Button
            onClick={handleSubmit}
            className="success-button"
            variant="outlined"
          >
            등록하기
          </Button>
        ) : (
          <Button
            className="disable-button"
            variant="outlined"
            size="large"
          >
            사진과 내용을 모두 입력하세요.
          </Button>
        )}
      </div>
      <div className="addBoard-body">
      <select
          value={categoryId}
          onChange={(e) => setCategoryId(e.target.value)}
        >
          <option value="" disabled>
            카테고리를 선택하세요.
          </option>
          {categories}
        </select>
        <ImageUploader setImage={setImage} preview_URL={image.preview_URL}/>
        <TextArea setTitle={setTitle} setPrice={setPrice} setDetail={setDetail} setLink={setLink} 
        title={title} price={price} detail={detail} link={link}/>
      </div>
    </div>
  );
}
export default UploadBoard;
