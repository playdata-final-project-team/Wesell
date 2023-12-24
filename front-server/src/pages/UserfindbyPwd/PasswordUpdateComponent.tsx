import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

const UpdatePasswordComponent = () => {
  const [uuid, setUuid] = useState<string | undefined>(undefined);
  const [pwd, setPwd] = useState('');
  const [rePwd, setRePwd] = useState('');

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