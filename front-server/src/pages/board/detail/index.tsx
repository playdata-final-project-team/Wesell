import React, { useState, useEffect } from 'react';
import PostDetail from 'components/PostContentBox';
import axios from 'axios';

const PostDetailPage = () => {
  const [postInfo, setPostInfo] = useState(null);
  const [postId] = useState();

  useEffect(() => {
    const fetchPostInfo = async () => {
      try {
        const response = await axios.get('http://localhost:8000/deal-service/api/v1/post', {
          params: { id: postId },
        });
        setPostInfo(response.data);
      } catch (error) {
        console.error('게시글 정보를 가져오는 중 오류 발생:', error);
      }
    };

    if (postId) {
      fetchPostInfo();
    }
  }, [postId]);

  return (
    <div>
      {postInfo ? (
        <PostDetail postInfo={postInfo} />
      ) : (
        <p>게시글 정보를 불러오는 중...</p>
      )}
    </div>
  );
};

export default PostDetailPage;
