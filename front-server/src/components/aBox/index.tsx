import './style.css';
import { ReactElement } from 'react';

interface Props {
  label: string;
  onClick?: () => void;
  href?: string;
  icon?: ReactElement;
}

const ABox = (props: Props) => {
  const { label, href, icon: Icon } = props;
  const { onClick } = props;
  return (
    <div className="a-box">
      {Icon && Icon}
      <a href={href} className="a-box-element" onClick={onClick}>
        {label}
      </a>
    </div>
  );
};

export default ABox;
