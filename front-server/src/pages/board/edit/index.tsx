import React, { useState, useEffect } from 'react';
import axios from 'axios';

const EditPostPage = () => {
  const [postId, setPostId] = useState(/**/);
  const [editPostInfo, setEditPostInfo] = useState({
    uuid: /* localStorage에서 uuid를 가져오는 코드 작성 */,
    categoryId: null,
    title: '',
    price: null,
    link: '',
    detail: '',
  });

  useEffect(() => {
    const fetchPostInfo = async () => {
      try {
        const response = await axios.get(`http://localhost:8000/deal-service/api/v1/post?id=${postId}`);
        const postInfo = response.data;
        setEditPostInfo({
          uuid: postInfo.uuid,
          categoryId: postInfo.categoryId,
          title: postInfo.title,
          price: postInfo.price,
          link: postInfo.link,
          detail: postInfo.detail,
        });
      } catch (error) {
        console.error('게시글 정보를 가져오는 중 오류 발생:', error);
      }
    };

    if (postId) {
      fetchPostInfo();
    }
  }, [postId]);

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setEditPostInfo((prevInfo) => ({
      ...prevInfo,
      [name]: value,
    }));
  };

  const handleEditSubmit = async () => {
    try {
      const response = await axios.put(`http://localhost:8000/api/v1/edit?id=${postId}`, editPostInfo);
      console.log('게시글 수정 완료:', response.data);
    } catch (error) {
      console.error('게시글 수정 중 오류 발생:', error);
    }
  };

  return (
    <div>
      <h2>Edit Post</h2>
      <form>
        <label>Title:</label>
        <input type="text" name="title" value={editPostInfo.title} onChange={handleInputChange} />

        <label>Price:</label>
        <input type="number" name="price" value={editPostInfo.price || ''} onChange={handleInputChange} />

        <label>Link:</label>
        <input type="text" name="link" value={editPostInfo.link} onChange={handleInputChange} />

        <label>Detail:</label>
        <textarea name="detail" value={editPostInfo.detail} onChange={handleInputChange} />

        <button type="button" onClick={handleEditSubmit}>
          수정 완료
        </button>
      </form>
    </div>
  );
};

export default EditPostPage;
