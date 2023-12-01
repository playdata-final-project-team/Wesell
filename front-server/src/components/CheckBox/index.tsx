import axios from 'axios';
import './style.css';

// interface: Check Box 컴포넌트 Props //
interface Props {
  name: string;
  label: string;
  id: string;
  value: number;
}

// component: Check Box 컴포넌트 //
const CheckBox = (props: Props) => {
  const { id, label, name, value } = props;

  // render: Check Box 컴포넌트 렌더링 //
  return (
    <>
      <input id={id} name={name} type="checkbox" value={value} />
      <label htmlFor={id}>{label}</label>
    </>
  );
};

export default CheckBox;
