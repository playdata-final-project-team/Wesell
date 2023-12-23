/* eslint-disable react/display-name */
import { forwardRef, ChangeEvent } from 'react';
import './style.css';

// interface: Input Box 컴포넌트 Props //
interface Props {
  type: 'text' | 'password';
  placeholder: string;
  name: string;
  value: string;
  error: boolean;

  isReadOnly?: boolean;
  icon?: 'eye-light-on-icon' | 'eye-light-off-icon';
  message?: string;

  onChange?: (event: ChangeEvent<HTMLInputElement>) => void;
  onIconClick?: () => void;
  onClick?: () => void;
  onKeyDown?: (event: React.KeyboardEvent<HTMLInputElement>) => void;
}

// component: Input Box 컴포넌트 //
const InputBox = forwardRef<HTMLInputElement, Props>((props: Props, ref) => {
  /**state: props */
  const { type, name, placeholder, error, value, icon, message } = props;
  const { onChange, onIconClick, onClick, onKeyDown, isReadOnly } = props;

  // event-handler: on Key-Down 이벤트 처리 함수 //
  const onKeyDownHandler = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (!onKeyDown) return;
    onKeyDown(event);
  };

  // render: Input Box 컴포넌트 //
  return (
    <div className="inputbox">
      <div className={error ? 'inputbox-container-error' : 'inputbox-container'}>
        <input
          className="input"
          name={name}
          ref={ref}
          type={type}
          placeholder={placeholder}
          value={value}
          onClick={onClick}
          onChange={onChange}
          onKeyDown={onKeyDownHandler}
          autoComplete="off"
          readOnly={isReadOnly}
        />

        {onIconClick !== undefined && (
          <div className="icon-button" onClick={onIconClick}>
            {icon !== undefined && <div className={`icon ${icon}`}></div>}
          </div>
        )}
      </div>
      {message !== undefined && <div className="inputbox-message">{message}</div>}
    </div>
  );
});

export default InputBox;
