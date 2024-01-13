import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import certNumStore from 'stores/cert-num.store';

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

const FoundSmsComponent = () => {
  const [phoneNumber, setPhoneNumber] = useState('');
  const [code, setCode] = useState('');
  const [userId, setUserId] = useState('');
  const [PhoneError, setPhoneError] = useState('');
  const [successMsg, setMsg] = useState('');
  const [AuthError, setAuthError] = useState('');
  const { certNum, setCertNum } = certNumStore();

  const navigate = useNavigate();

  const handleSMS = async () => {
    if (!phoneNumber) {
      setPhoneError('휴대폰 번호를 입력하세요.');
      return;
    }

    try {
      const response = await axios.get(`/auth-server/api/v1/phone/validate`, {
        params: {
          phoneNumber: phoneNumber,
        },
      });
      console.log('서버 응답:', response.data);
      setCertNum(response.data);
      setPhoneError('');
      setMsg('✅ 인증정보가 전송되었습니다. 인증번호를 입력해주세요.');
      // setCode(response.data.code);
    } catch (error) {
      console.error('에러 발생:', error);
      setPhoneError('😒 서버 오류로 인해 통신 오류가 발생했습니다. 잠시후 다시 시도해주세요!');
    }
  };

  const handleSendPhoneForPWD = async () => {
    try {
      if (!code) {
        setAuthError('인증번호를 받아오세요.');
        return;
      }

      if (certNum.toString() !== code) {
        setAuthError('인증번호가 일치하지 않습니다.');
        return;
      }

      const response = await axios.post(
        '/auth-server/api/v1/send/id/phone',
        { phoneNumber, code },
        { headers: { 'Content-Type': 'application/json' } },
      );
      setAuthError('');
      setMsg('🎉 인증정보가 일치합니다.다음페이지로 이동하겠습니다.');
      setTimeout(() => {
        navigate(`/update-pw/${response.data}`);
      }, 1800);
    } catch (err) {
      if (axios.isAxiosError(err) && err.response) {
        setAuthError(err.response.data.message || '인증정보가 일치하지 않습니다.');
      } else {
        setAuthError('An unknown error occurred');
      }
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
      <h1>SMS 인증</h1>
      <div style={{ display: 'flex', flexDirection: 'column', height: '110px' }}>
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <RoundInput
            type="text"
            value={phoneNumber}
            onChange={(e) => setPhoneNumber(e.target.value)}
            placeholder="휴대폰 번호를 입력하세요"
          />
          <RoundButton onClick={handleSMS}>SMS 전송</RoundButton>
        </div>
        {PhoneError && <p style={{ color: 'red', marginLeft: '13px' }}>{PhoneError}</p>}
      </div>

      <div style={{ display: 'flex', flexDirection: 'column', height: '110px' }}>
        <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <RoundInput
            type="text"
            value={code}
            onChange={(e) => setCode(e.target.value)}
            placeholder="인증번호 입력"
          />
          <RoundButton onClick={handleSendPhoneForPWD}>확인</RoundButton>
        </div>
        {AuthError && <p style={{ color: 'red', marginLeft: '13px' }}>{AuthError}</p>}
      </div>

      {userId && <p>User ID: {userId}</p>}
    </div>
  );
};

export default FoundSmsComponent;
