import './style.css';

// interface: Button Box 인터페이스//
interface Props {
  label: string;
  type: 'button' | 'submit' | 'reset' | undefined;
  onClick?: () => void;
  isEnable: boolean;
}

// component: Button Box 컴포넌트 //
const ButtonBox = (props: Props) => {
  const { label, type, onClick, isEnable } = props;

  // render: Button Box 컴포넌트 렌더링 //
  return (
    <div className="button-box">
      {isEnable && <button className="enable-btn" type={type} onClick={onClick}>{label}</button>}
      {!isEnable && <button className="disable-btn" type={type} disabled>{label}</button>}
    </div>
  );
};

export default ButtonBox;
