import SideButtonBox from 'components/SideButtonBox';
import React from 'react';

// component: 사이드버튼 컴포넌트//
export default function RightAside() {
  // render: 사이드버튼 컴포넌트 렌더링//
  return (
    <div className="right-aside-wrapper">
      <SideButtonBox isUser={false} isAdmin={false} uuid={''} />
    </div>
  );
}
