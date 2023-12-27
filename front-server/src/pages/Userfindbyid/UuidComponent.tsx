import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';

const RoundInput = styled.input`
  width: 500px;
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
  font-size: 20px;
  border: none;
  cursor: pointer;
  margin: 40px;
`;

const UuidComponent = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [code, setCode] = useState('');
  const [userId, setUserId] = useState('');
  const [SMSError, setSMSError] = useState('');
  const [IDError, setIDError] = useState('');
  const navigate = useNavigate();

  const handleSendSMS = async () => {
    try {
      if (!phoneNumber) {
        setSMSError('휴대폰 번호를 입력해주세요');
        return;
      }

      const response = await axios.get(`/auth-server/api/v1/phone/validate`, {
        params: {
          phoneNumber: phoneNumber,
        },
      });
      console.log('서버 응답:', response.data);
      setSMSError('인증정보를 보내드리겠습니다.'); // 성공적으로 전송되면 에러 상태 초기화

    } catch (error) {
      console.error('에러 발생:', error);
      setSMSError('휴대폰 번호가 일치하지 않습니다.');
    }
  };

  const handleSendPhoneForID = async () => {
    try {
      if (!code) {
        setIDError('인증번호를 입력하세요.');
        return;
      }

      const response = await axios.post(
        'auth-server/api/v1/send/id/phone',
        { phoneNumber, code },
        { headers: { 'Content-Type': 'application/json' } }
      );
      setIDError('인증정보가 일치합니다.다음페이지로 이동하겠습니다.'); // 성공적으로 전송되면 에러 상태 초기화
      
      setTimeout(() => {
      navigate(`/found-email/${response.data}`);
    },2000);}
     catch (err) {
      if (axios.isAxiosError(err) && err.response) {
        setIDError(err.response.data.message || '인증정보가 일치하지 않습니다.');
      } else {
        setIDError('An unknown error occurred');
      }
    }
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '200px' }}>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <div style={{ position: 'relative', marginRight: '10px' }}>
          <RoundInput
            type="text"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            placeholder="phone number"
          />
          <RoundButton onClick={handleSendSMS} style={{ position: 'absolute', right: 0 }}>
            인증번호 전송
          </RoundButton>
        </div>
      </div>

      <p style={{ color: 'red', marginTop: '10px' }}>{SMSError}</p>
  
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
        <div style={{ position: 'relative', marginRight: '10px' }}>
          <RoundInput
            type="text"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            placeholder="인증번호 입력"
          />
          <RoundButton onClick={handleSendPhoneForID} style={{ position: 'absolute', right: 0 }}>
            확인
          </RoundButton>
        </div>
      </div>
  
      <p style={{ color: 'red', marginTop: '10px' }}>{IDError}</p>
  

      {userId && <p>User ID: {userId}</p>}
      <div>
    
      </div>
    </div>
  );
};

export default UuidComponent;