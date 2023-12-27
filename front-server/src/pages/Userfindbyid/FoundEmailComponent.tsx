import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import styled from 'styled-components';

const FoundEmailComponent = () => {
  const [uuid, setUuid] = useState('');
  const [foundEmails, setFoundEmails] = useState<string[]>([]);
  const [error, setError] = useState('');
  const { uuid: paramUuid } = useParams();

  useEffect(() => {
    handleFindEmails();
    console.log(uuid);
  }, [paramUuid]);

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

  console.log(foundEmails);

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '100vh', fontSize: '50px', marginTop: '-150px' }}>
      {foundEmails.length > 0 ? (
        <div>
          <h1>아이디 찾기 완료!</h1>
          <ul>
            {foundEmails.map((email, index) => (
              <li key={index}>{email}</li>
            ))}
          </ul>
        </div>
      ) : (
        <p style={{ fontSize: '24px' }}>{error || '이메일이 없습니다.'}</p>
      )}
    </div>
  );
};

export default FoundEmailComponent;