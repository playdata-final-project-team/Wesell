import React from 'react';
import PropTypes from 'prop-types';

interface PostInfo {
    title: string;
    createdAt: string;
    price: number;
    detail: string;
    nickname: string;
    imageUrl: string;
    link: string;
  }
  interface PostDetailProps {
    postInfo: PostInfo;
  }

const PostDetail: React.FC<PostDetailProps> = ({ postInfo }) => {
  return (
    <div>
      <h2>{postInfo.title}</h2>
      <p>Created At: {postInfo.createdAt}</p>
      <p>Price: {postInfo.price}</p>
      <p>Detail: {postInfo.detail}</p>
      <p>Nickname: {postInfo.nickname}</p>
      <img src={postInfo.imageUrl} alt="Post Image" />
      <a href={postInfo.link} target="_blank" rel="noopener noreferrer">
        View on KakaoTalk
      </a>
    </div>
  );
};

// PropTypes 추가
PostDetail.propTypes = {
  postInfo: PropTypes.shape({
    title: PropTypes.string.isRequired,
    createdAt: PropTypes.string.isRequired,
    price: PropTypes.number.isRequired,
    detail: PropTypes.string.isRequired,
    nickname: PropTypes.string.isRequired,
    imageUrl: PropTypes.string.isRequired,
    link: PropTypes.string.isRequired,
  }).isRequired,
};

export default PostDetail;
