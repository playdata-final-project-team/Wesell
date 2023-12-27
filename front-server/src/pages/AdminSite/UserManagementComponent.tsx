import React, { useEffect, useState } from 'react';
import axios from 'axios';

type versoinConroller  = {
  jsVersion: string;
  agree: string;
  cssVersion: string;
  title: string;
}

const UserManagementComponent = () => {
  const [versions, setVersions] = useState<versoinConroller>({jsVersion: "", agree: "", cssVersion: "", title: ""});

  useEffect(() => {
    // 서버에서 버전 값을 가져오는 함수
    const fetchVersions = async () => {
      try {
        const response = await axios.get<versoinConroller>('/admin-service/api/v1/get-version');
        setVersions(response.data);
      } catch (error) {
        console.error('Error fetching versions:', error);
      }
    };

    fetchVersions();
  }, []);

  return (
    <div>
      <h2>버전 정보</h2>
      <ul>
        {Object.entries(versions).map(([key, value]) => (
          <li key={key}>
            <strong>{key}:</strong> {value as React.ReactNode}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default UserManagementComponent;