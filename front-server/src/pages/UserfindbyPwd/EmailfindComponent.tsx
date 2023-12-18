import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const EmailfindComponent = () => {
  const [email, setEmail] = useState('');
  const [uuid, setUuid] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate(); // useNavigate 훅을 사용하여 navigate 함수를 가져옴

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handleFindEmail = async () => {
    try {
      const response = await axios.get(`/auth-server/find/pwd/email?email=${email}`);
      setUuid(response.data);
      setError('');

      // UUID를 찾았을 때 다음 페이지로 이동
      navigate(`/testJiho5/${response.data}`);
    } catch (error) {
      setUuid('');
      setError('사용자를 찾을 수 없습니다');
    }
  };

  return (
    <div>
      <label>
        이메일 입력:
        <input type="text" value={email} onChange={handleEmailChange} />
      </label>
      <button onClick={handleFindEmail}>UUID 찾기</button>
      {uuid && <p>UUID: {uuid}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default EmailfindComponent;