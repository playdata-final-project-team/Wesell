import './style.css';

// component: 헤더 레이아웃 //
export default function Header() {
  // render: 헤더 레이아웃 컴포넌트 렌더링 //
  return (
    <div id="header">
      <div className="header-container">
        <div className="header-left-box">
          <div className="icon-box">
            <a href="/">
              <div className="icon logo-wesell-icon"></div>
            </a>
          </div>
        </div>
        <div className="header-right-box"></div>
      </div>
    </div>
  );
}
