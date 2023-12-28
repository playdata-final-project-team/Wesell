import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import styled from 'styled-components';

const FoundEmailComponent = () => {
  const [foundEmails, setFoundEmails] = useState<string[]>([]);
  const [error, setError] = useState('');
  const { uuid: paramUuid } = useParams();

  const navigator = useNavigate();

  useEffect(() => {
    handleFindEmails();
  }, [paramUuid]);

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

  const handleFindEmails = async () => {
    try {
      if (!paramUuid) {
        return;
      }

      const response = await axios.get(`/auth-server/api/v1/find/email/${paramUuid}`);
      const emails = response.data;
      setFoundEmails([emails]);
      setError('');
    } catch (error) {
      if (axios.isAxiosError(error) && error.response && error.response.status === 404) {
        setError('해당 UUID에 대한 이메일이 없습니다.');
      } else {
        setError('이메일을 찾는 도중에 오류가 발생했습니다.');
      }
      setFoundEmails([]);
    }
  };

  const handleMoveToFindPw = () => {
    navigator('/find-pw');
  };

  return (
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '100vh',
        fontSize: '30px',
        marginTop: '-150px',
      }}
    >
      {foundEmails.length > 0 ? (
        <div>
          <h1>ID :</h1>
          <ul>
            {foundEmails.map((email, index) => (
              <li key={index}>{email}</li>
            ))}
          </ul>
        </div>
      ) : (
        <p style={{ fontSize: '24px' }}>{error || '이메일이 없습니다.'}</p>
      )}
      <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
        <RoundButton onClick={handleMoveToFindPw}>비밀번호 찾기</RoundButton>
      </div>
    </div>
  );
};

export default FoundEmailComponent;
