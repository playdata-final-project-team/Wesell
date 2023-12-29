import { RevolvingDot } from 'react-loader-spinner';

const Loading: React.FC = () => {
  return (
    <div className="spinner">
      <RevolvingDot color="#15f1f5" height={'200px'} width={'200px'} />
    </div>
  );
};

export default Loading;
