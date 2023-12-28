import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styled from 'styled-components';

const RoundInput = styled.input`
  width: 300px;
  height: 50px;
  border-radius: 50px; /* 둥근 테두리를 위한 값 설정 */
  padding: 15px;
  font-size: 20px;
  margin-bottom: 10px;
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
  margin: 10px;
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
      if (!uuid) {
        console.error('UUID is undefined');
        return;
      } else if (!pwd) {
        setError('비밀번호를 입력 바랍니다.');
        return;
      } else if (!rePwd) {
        setError('비밀번호를 확인해주시길 바랍니다.');
      }``

      if (pwd !== rePwd) {
        setError('비밀번호가 일치하지 않습니다.');
        return;
      }

      setError('비밀번호가 수정되었습니다. 로그인페이지로 이동합니다.');
      setTimeout(() => {
        navigate('/auth');
        return;
      }, 1800);

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
    <div
      style={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '300px',
      }}
    >
      <div>
        <div>
          <RoundInput
            type="password"
            value={pwd}
            onChange={(e) => {
              setPwd(e.target.value);
              setError('');
            }}
            placeholder="새 비밀번호를 입력하세요"
          />
        </div>
      </div>

      <div>
        <div>
          <RoundInput
            type="password"
            value={rePwd}
            onChange={(e) => {
              setRePwd(e.target.value);
              setError('');
            }}
            placeholder="비밀번호 확인"
          />
        </div>
      </div>
      <p
        style={{
          color: 'red',
          height: '35px',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
        }}
      >
        {error}
      </p>

      <div>
        <RoundButton onClick={handleUpdatePassword}>비밀번호 수정</RoundButton>
      </div>
    </div>
  );
};

export default UpdatePasswordComponent;
