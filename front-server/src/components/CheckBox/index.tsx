import './style.css';

// interface: Check Box 컴포넌트 Props //
interface Props{
    name: string;
    label: string;
    id: string;
}

// component: Check Box 컴포넌트 //
const CheckBox = (props: Props) => {

    const {id, label, name} = props;

    // render: Check Box 컴포넌트 렌더링 //
    return(
        <>
            <input id ={id} name={name} type='checkbox' value={1}/>
            <label htmlFor={id}>{label}</label>
        </>
    );
}

export default CheckBox;