import axios from 'axios';
import './style.css';

// interface: Check Box 컴포넌트 Props //
interface Props {
  name: string;
  label: string;
  id: string;
  checked: boolean; 
  onChange : (event: React.ChangeEvent<HTMLInputElement>) => void;
  onKeyDown?: (event: React.KeyboardEvent<HTMLInputElement>) => void;
}

// component: Check Box 컴포넌트 //
const CheckBox = (props: Props) => {
  const { id, label, name, checked } = props;
  const {onChange, onKeyDown} = props;

  // event-handler: on Key-Down 이벤트 처리 함수 //
  const onKeyDownHandler = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (!onKeyDown) return;
    onKeyDown(event);
  };

  // render: Check Box 컴포넌트 렌더링 //
  return (
    <>
      <input id={id} name={name} type="checkbox" onChange={onChange} onKeyDown={onKeyDownHandler} checked={checked}/>
      <label htmlFor={id}>{label}</label>
    </>
  );
};

export default CheckBox;
