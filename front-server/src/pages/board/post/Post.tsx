import React, { useEffect, useState } from 'react';
import axios from 'axios';
import './Post.css';

function GetCategory() {
  const [category, setCategory] = useState({});

  useEffect(() => {
    axios.get('http://localhost:8888/deal-service/category').then((response)=> {
      setCategory(response.data);
    })
  }, []);

  const categories = (Object.values(category) as Array<{ id: number; displayName: string }>).map((item) => (
    <option key={item.id} value={item.id}>
      {item.displayName}
    </option>
  ));

  return categories;
}

function UploadBoard() {

  const [imageFile, setImageFile] = useState<File | undefined>(undefined);
  const categories = GetCategory();
  const [title, setTitle] = useState('');
  const [categoryId, setCategoryId] = useState(1);
  const [price, setPrice] = useState('');
  const [detail, setDetail] = useState('');
  const [link, setLink] = useState('');

  const body = {
    title: title,
    categoryId : categoryId,
    content: detail,
    price: price,
    link: link,
    }

    const HandleSubmit = async(body : string) => {

        const response = await axios.post('http://localhost:8888/deal-service/post', body);
        const postId = response.data.id;
      
        // 2번 API 호출: 이미지 업로드
        if (imageFile) {
          const formData = new FormData();
          formData.append('postId', postId);
          formData.append('file', imageFile);
      
          await axios.post('http://localhost:8888/deal-service/upload', formData, {
            headers: {
              'Content-Type': 'multipart/form-data',
            },
          });
        }
        
    }

    const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const fileInput = e.target as HTMLInputElement;
        const selectedFile = fileInput.files?.[0];
    
        if (selectedFile) {
          setImageFile(selectedFile);
        }
      };

  return (<>
    <div className="post-view-wrapper">
        {/* <form onSubmit={HandleSubmit}> */}
        <div className="post-view-row"> 
        <label>이미지</label>
        <input type="file" onChange={handleImageChange} />
        </div>
        <div className="post-view-row">
        <label>제목</label>
        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}></input>
    </div>
    <div className="post-view-row">
        <label>카테고리 선택</label>
        <select onChange={(event) => setCategoryId(parseInt(event.target.value))}>
          {categories}
        </select>
    </div>
    <div className="post-view-row">
        <label>가격</label>
        <input type="text" value={price} onChange={(e) => setPrice(e.target.value)}></input>
    </div>
    <div className="post-view-row">
        <label>내용</label>
        <input type="text" value={detail} onChange={(e) => setDetail(e.target.value)}></input>
    </div>
    <div className="post-view-row">
        <label>오픈 카카오톡 채팅 링크</label>
        <input type="text" value={link} onChange={(e) => setLink(e.target.value)}></input>
    </div>
        {/* </form> */}
    
    <button className="post-view-go-list-btn" onClick={() => HandleSubmit(JSON.stringify(body))}>등록</button>
    </div>
    </>)
}
  
export default UploadBoard;