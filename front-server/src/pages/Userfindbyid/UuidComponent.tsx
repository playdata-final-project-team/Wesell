import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const UuidComponent = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [code, setCode] = useState('');
  const [userId, setUserId] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();


  const handleSendSMS = async () => {
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

    } catch (error) {
      console.error('에러 발생:', error);
      setError('서버 요청 중에 문제가 발생했습니다.');
    }
  };

  const handleSendPhoneForID = async () => {
    try {
      const response = await axios.post(
        'auth-server/send/id/phone',
        { phoneNumber, code },
        { headers: { 'Content-Type': 'application/json' } }
      );

      setUserId(response.data);
      setError('');

      navigate(`/found-email/${response.data}`);
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
      <button onClick={handleSendSMS}>SMS 전송</button>

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
      <button onClick={handleSendPhoneForID}>Send Phone for ID</button>
      <br />
      {userId && <p>User ID: {userId}</p>}
    </div>
  );
};

export default UuidComponent;