import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';


const RoundInput = styled.input`
  width: 450px;
  border-radius: 70px; /* 둥근 테두리를 위한 값 설정 */
  padding: 50px;
  font-size: 25px;
`;

const RoundButton = styled.button`
  width: 350px;
  height: 70px;
  border-radius: 70px; /* 둥근 테두리를 위한 값 설정 */
  background-color: #00A8CC;
  color: #fff;
  font-size: 30px;
  border: none;
  cursor: pointer;
  margin: 40px;
`;

const UpdatePasswordComponent = () => {
  const [uuid, setUuid] = useState<string | undefined>(undefined);
  const [pwd, setPwd] = useState('');
  const [rePwd, setRePwd] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const { uuid: paramUuid } = useParams();

  useEffect(() => {
    if (paramUuid) {
      setUuid(paramUuid);
    }
  }, [paramUuid]);

  const handleUpdatePassword = async () => {
    try {
      console.log('Updating password...');

      if (!uuid) {
        console.error('UUID is undefined');
        return;
      }

      if (pwd !== rePwd) {
        setError('비밀번호가 일치하지 않습니다.');
        return;
      }
      if (pwd == rePwd) {
        setError('비밀번호가 수정되었습니다. 로그인페이지로 이동합니다.');
        setTimeout(() => {
        navigate('/auth');
        return;
      },1800);}

      const response = await axios.post(`/auth-server/api/v1/update/pwd/${uuid}`, {
        pwd: pwd,
        rePwd: rePwd,
      });

      console.log('Response:', response.data);

      console.log('Password updated successfully!');
    } catch (error) {
      console.error('Error updating password:', error);
    }
  };

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', justifyContent: 'center', minHeight: '100vh' }}>
      <div style={{ position: 'relative', marginBottom: '90px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ position: 'relative', marginTop: '-150px' }}>
          <RoundInput
            type="password"
            value={pwd}
            onChange={(e) => setPwd(e.target.value)}
            placeholder="새 비밀번호를 입력하세요"
          />
        </div>
      </div>

      <div style={{ position: 'relative', marginBottom: '30px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
        <div style={{ position: 'relative', marginTop: '-50px' }}>
          <RoundInput
            type="password"
            value={rePwd}
            onChange={(e) => setRePwd(e.target.value)}
            placeholder="비밀번호 확인"
          />
        </div>
      </div>
      {error && <p style={{ color: 'red', marginBottom: '10px' }}>{error}</p>}

      <div>
        <RoundButton onClick={handleUpdatePassword}>
          비밀번호 수정
        </RoundButton>
      </div>
    </div>
  );
};

export default UpdatePasswordComponent;
