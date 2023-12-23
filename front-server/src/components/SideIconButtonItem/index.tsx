import './style.css';

interface Props {
  icon: string;
  alt: string;
  tooltip: string;
  onIconClick?: () => void;
}

export default function SideIconButtonItem(props: Props) {
  const { icon, alt, tooltip } = props;
  const { onIconClick } = props;

  return (
    <div>
      {onIconClick !== undefined && (
        <div className="side-icon-button" onClick={onIconClick} title={tooltip}>
          {icon !== undefined && <div className={`side-icon ${icon}`}></div>}
        </div>
      )}
      {onIconClick === undefined && alt}
    </div>
  );
}
