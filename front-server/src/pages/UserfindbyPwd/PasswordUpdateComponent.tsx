import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const UpdatePasswordComponent = () => {
  const [uuid, setUuid] = useState<string | undefined>(undefined); // 초기 상태를 undefined로 설정
  const [pwd, setPwd] = useState('');
  const [rePwd, setRePwd] = useState('');

  const { uuid: paramUuid } = useParams();

  useEffect(() => {
    // URL 매개변수로 전달된 UUID 값을 설정
    if (paramUuid) {
      setUuid(paramUuid);
    }
  }, [paramUuid]);

  const handleUpdatePassword = async () => {
    try {
      console.log('Updating password...');

      if (!uuid) {
        console.error('UUID is undefined'); // 이 부분이 추가되었습니다.
        return;
      }

      const response = await axios.post(`/auth-server/update/pwd/${uuid}`, {
        pwd: pwd,
        rePwd: rePwd,
      });

      console.log('Response:', response.data);

      // 성공 시 처리, 예를 들어 사용자에게 성공 메시지 표시
      console.log('Password updated successfully!');
    } catch (error) {
      console.error('Error updating password:', error);

      // 오류 처리, 예를 들어 사용자에게 오류 메시지 표시
    }
  };

  return (
    <div>

      <label>Password:</label>
      <input type="password" value={pwd} onChange={(e) => setPwd(e.target.value)} />

      <label>Re-enter Password:</label>
      <input type="password" value={rePwd} onChange={(e) => setRePwd(e.target.value)} />

      <button onClick={handleUpdatePassword}>Update Password</button>
    </div>
  );
};

export default UpdatePasswordComponent;