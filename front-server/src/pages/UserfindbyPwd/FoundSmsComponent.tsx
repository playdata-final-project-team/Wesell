import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const RoundInput = styled.input`
  width: 550px;
  border-radius: 70px; /* 둥근 테두리를 위한 값 설정 */
  padding: 50px;
  font-size: 25px;
`;

const RoundButton = styled.button`
  width: 150px;
  height: 70px;
  border-radius: 70px; /* 둥근 테두리를 위한 값 설정 */
  background-color: #00A8CC;
  color: #fff;
  font-size: 20px;
  border: none;
  cursor: pointer;
  margin: 40px;
`;

const FoundSmsComponent = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [code, setCode] = useState('');
  const [userId, setUserId] = useState('');
  const [PhoneError, setPhoneError] = useState('');
  const [AuthError, setAuthError] = useState('');

  const navigate = useNavigate();

  const handleSMS = async () => {
    try {
      if (!phoneNumber) {
        setPhoneError('휴대폰 번호를 입력하세요.');
        return;
      }

  const response = await axios.get(`/auth-server/api/v1/phone/validate`, {
        params: {
          phoneNumber: phoneNumber,
        },
      });

      console.log('서버 응답:', response.data);
      setPhoneError('인증정보를 보내드리겠습니다.');
      setCode(response.data.code);
    } catch (error) {
      console.error('에러 발생:', error);
      setPhoneError('휴대폰 번호가 일치하지 않습니다.');
    }
  };

  const handleSendPhoneForPWD = async () => {
    try {
      if (!code) {
        setAuthError('인증번호를 받아오세요.');
        return;
      }

      const response = await axios.post(
        '/auth-server/api/v1/send/id/phone',
        { phoneNumber, code },
        { headers: { 'Content-Type': 'application/json' } }
      );

      setAuthError('인증정보가 일치합니다 다음페이지로 이동하겠습니다.');
      setTimeout(() => {
      navigate(`/testJiho6/${response.data}`);
    },1800);}
     catch (err) {
      if (axios.isAxiosError(err) && err.response) {
        setAuthError(err.response.data.message || '인증정보가 일치하지 않습니다.');
      } else {
        setAuthError('An unknown error occurred');
      }
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '100vh' }}>
      <div style={{ position: 'relative', marginBottom: '50px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ position: 'relative', marginTop: '-200px' }}>
          <RoundInput
            type="text"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            placeholder="휴대폰 번호를 입력하세요"
          />
          {PhoneError && <p style={{ color: 'red', marginTop: '30px',textAlign: 'center' }}>{PhoneError}</p>}
          <RoundButton onClick={handleSMS} style={{ position: 'absolute', right: 0, top: '20%', transform: 'translateY(-50%)' }}>
            SMS 전송
          </RoundButton>
        </div>
      </div>
  
      <div style={{ position: 'relative', marginBottom: '30px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ position: 'relative', marginTop: '-50px' }}>
          <RoundInput
            type="text"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            placeholder="인증정보를 입력하세요"
          />
          {AuthError && <p style={{ color: 'red', marginTop: '10px',textAlign: 'center' }}>{AuthError}</p>}
          <RoundButton onClick={handleSendPhoneForPWD} style={{ position: 'absolute', right: 0, top: '20%', transform: 'translateY(-50%)' }}>
            Send Phone for ID
          </RoundButton>
        </div>
      </div>
  
      {userId && <p>User ID: {userId}</p>}
    </div>
  );
};

export default FoundSmsComponent;