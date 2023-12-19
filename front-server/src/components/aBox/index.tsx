import './style.css';

interface Props{
    label: string;
    onClick ?: () => void;
    href ?: string;
}

const ABox = (props: Props) => {
    const {label, href} = props;
    const {onClick} = props;
    return(
        <div className="a-box">
            <a href={href} className="a-box-element" onClick={onClick}>{label}</a>
        </div>
    );
}

export default ABox;