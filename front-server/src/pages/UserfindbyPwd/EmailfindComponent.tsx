import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const RoundInput = styled.input`
  width: 550px;
  border-radius: 70px; /* 둥근 테두리를 위한 값 설정 */
  padding: 50px;
  font-size: 35px;
`;

const RoundButton = styled.button`
  width: 150px;
  height: 70px;
  border-radius: 70px; /* 둥근 테두리를 위한 값 설정 */
  background-color: #00A8CC;
  color: #fff;
  font-size: 16px;
  border: none;
  cursor: pointer;
  margin: 40px;
`;

const EmailfindComponent = () => {
  const [email, setEmail] = useState('');
  const [uuid, setUuid] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate(); 

  const handleEmailChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  const handleFindEmail = async () => {
    try {
      const response = await axios.get(`/auth-server/api/v1/find/pwd/email?email=${email}`);
      setError('이메일이 일치합니다.다음페이지로 이동하겠습니다.');
      
      setTimeout(() => {
      navigate(`/testJiho5/${response.data}`);
    },1800);} catch (error) {
      setUuid('');
      setError('사용자를 찾을 수 없습니다');
    }
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '200px' }}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <div style={{ position: 'relative', marginRight: '10px' }}>
      <label>
        <RoundInput 
        type="text" 
        value={email} 
        onChange={handleEmailChange}
        placeholder="이메일을 입력해주세요" />
      </label>
      <RoundButton onClick={handleFindEmail}style={{ position: 'absolute', right: 0 }}>이메일 찾기</RoundButton>
      {uuid && <p>UUID: {uuid}</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
        </div>
      </div>
    </div>
  );
};

export default EmailfindComponent;