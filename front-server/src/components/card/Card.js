import './Card.css';
import { useNavigate } from 'react-router-dom';

export const Card = ({ board_id, title, img_url, price }) => {
  const navigate = useNavigate();
  return (
    <div
      className="card-wrapper"
      onClick={() => {
        navigate(`/product/detail/${board_id}`);
      }}
    >
      <div className="card-body-img">
        <img src={img_url} />
      </div>
      <div className="card-body-text">
        <div className="card-body-text-title">{title}</div>
        <div className="card-body-text-content">{price}</div>
      </div>
    </div>
  );
};
