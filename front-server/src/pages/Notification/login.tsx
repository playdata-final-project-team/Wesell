import React from 'react';

const LoginPage: React.FC = () => {
const handleClick = () => {
const userId = (document.getElementById('user_id') as HTMLInputElement)?.value;
if (!userId) {
alert('userId cannot be empty');
return;
}
localStorage.setItem('userId', userId);
location.href = './index';
};

return (
<html>
<head>
  <meta charSet="utf-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1" />
  <title>Web Push - login</title>
  <link rel="stylesheet" as="style" crossOrigin="anonymous" href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css" />
  <link rel="stylesheet" href="/css/common.css" />
</head>
<body>
<header>
  <h1>Web Push</h1>
</header>
<main>
  <section>
    <h2>👋 Login</h2>
    <div className="group">
      <input className="fill" id="user_id" type="text" placeholder="User ID" />
      <button onClick={handleClick}>Go!</button>
    </div>
  </section>
</main>
</body>
</html>
);
};

export default LoginPage;
