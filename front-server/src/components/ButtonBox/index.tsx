// interface: Button Box 인터페이스//
interface Props {
  label: string;
  type: 'button' | 'submit' | 'reset' | undefined;
  onClick?: () => void;
}

// component: Button Box 컴포넌트 //
const ButtonBox = (props: Props) => {
  const { label, type, onClick } = props;

  // render: Button Box 컴포넌트 렌더링 //
  return (
    <div className="button-box">
      <button className="btn " type={type} onClick={onClick}>
        {label}
      </button>
    </div>
  );
};

export default ButtonBox;
