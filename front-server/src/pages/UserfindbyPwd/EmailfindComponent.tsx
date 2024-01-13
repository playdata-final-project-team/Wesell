import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const RoundInput = styled.input`
  width: 300px;
  border-radius: 50px; /* 둥근 테두리를 위한 값 설정 */
  padding: 15px;
  font-size: 20px;
`;

const RoundButton = styled.button`
  width: 110px;
  height: 40px;
  border-radius: 50px; /* 둥근 테두리를 위한 값 설정 */
  background-color: #00a8cc;
  color: #fff;
  font-size: 15px;
  border: none;
  cursor: pointer;
  margin-left: 10px;
`;

const EmailfindComponent = () => {
  const [email, setEmail] = useState('');
  const [uuid, setUuid] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleFindEmail = async () => {
    if (!email) {
      setError('이메일을 입력해주세요.');
      return;
    }

    try {
      const response = await axios.get(`/auth-server/api/v1/find/pwd/email?email=${email}`);
      setError('이메일이 일치합니다.다음페이지로 이동하겠습니다.');

      setTimeout(() => {
        navigate(`/phone/valid/${response.data}`);
      }, 1800);
    } catch (error) {
      setUuid('');
      setEmail('');
      setError('사용자를 찾을 수 없습니다');
    }
  };

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <h1>비밀번호 찾기</h1>
      <div style={{ display: 'flex', flexDirection: 'column', height: '110px' }}>
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <RoundInput
            type="text"
            value={email}
            onChange={(e) => {
              setEmail(e.target.value);
              setError('');
            }}
            placeholder="이메일을 입력해주세요"
          />
          <RoundButton onClick={handleFindEmail}>이메일 인증</RoundButton>
        </div>
        {uuid && <p>UUID: {uuid}</p>}
        {error && <p style={{ color: 'blue', marginLeft: '13px' }}>{error}</p>}
      </div>
    </div>
  );
};

export default EmailfindComponent;
