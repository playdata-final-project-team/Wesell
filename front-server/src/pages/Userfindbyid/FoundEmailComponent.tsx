import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const FoundEmailComponent = () => {
  const [uuid, setUuid] = useState('');
  const [foundEmails, setFoundEmails] = useState<string[]>([]);
  const [error, setError] = useState('');
  const { uuid: paramUuid } = useParams();

  useEffect(() => {
    // paramUuid가 변경될 때마다 handleFindEmails 함수 호출
    handleFindEmails();
    console.log(uuid);
  }, [paramUuid]);

  const handleFindEmails = async () => {
    try {
      // paramUuid가 undefined인 경우, 함수 종료
      if (!paramUuid) {
        return;
      }

      const response = await axios.get(`/auth-server/find/email/${paramUuid}`);
      const emails = response.data;
      setFoundEmails([emails]);
      setError('');
    } catch (error) {
      // 에러 객체를 확인하여 메시지 설정
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
    <div>
  
    
      {/* 버튼 제거 */}
      {foundEmails.length > 0 ? (
        <div>
          <p>찾은 이메일:</p>
          <ul>
            {foundEmails.map((email, index) => (
              <li key={index}>{email}</li>
            ))}
          </ul>
        </div>
      ) : (
        <p>{error || '이메일이 없습니다.'}</p>
      )}
    </div>
  );
};

export default FoundEmailComponent;