import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const FoundSmsComponent = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [code, setCode] = useState('');
  const [userId, setUserId] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleSMS = async () => {
    try {
      if (!phoneNumber) {
        setError('휴대폰 번호를 입력하세요.');
        return;
      }

  const response = await axios.get(`/auth-server/api/v1/phone/validate`, {
        params: {
          phoneNumber: phoneNumber,
        },
      });

      console.log('서버 응답:', response.data);

      // 서버로부터 인증 번호를 받아오고, state에 저장
      setCode(response.data.code);
    } catch (error) {
      console.error('에러 발생:', error);
      setError('서버 요청 중에 문제가 발생했습니다.');
    }
  };

  const handleSendPhoneForPWD = async () => {
    try {
      if (!code) {
        setError('인증번호를 받아오세요.');
        return;
      }

      const response = await axios.post(
        '/auth-server/send/id/phone',
        { phoneNumber, code },
        { headers: { 'Content-Type': 'application/json' } }
      );

      setUserId(response.data);
      setError('');
      navigate(`/testJiho6/${response.data}`); // userId를 이용해 경로를 생성
    } catch (err) {
      if (axios.isAxiosError(err) && err.response) {
        setError(err.response.data.message || 'An error occurred');
      } else {
        setError('An unknown error occurred');
      }
    }
  };

  return (
    <div>
      <h1>Find ID</h1>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <label>
        Phone Number:
        <input
          type="text"
          value={phoneNumber}
          onChange={(e) => setPhoneNumber(e.target.value)}
          placeholder="휴대폰 번호를 입력하세요"
        />
        <button onClick={handleSMS}>SMS 전송</button>

        {error && <p style={{ color: 'red' }}>{error}</p>}
      </label>
      <br />
      <label>
        Code:
        <input
          type="text"
          value={code}
          onChange={(e) => setCode(e.target.value)}
        />
      </label>
      <br />
      <button onClick={handleSendPhoneForPWD}>Send Phone for ID</button>
      <br />
      {userId && <p>User ID: {userId}</p>}
    </div>
  );
};

export default FoundSmsComponent;