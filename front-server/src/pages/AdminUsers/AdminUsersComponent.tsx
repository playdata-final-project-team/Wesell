import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';

interface User {
  uuid: string;
  nickname: string;
  name: string;
  phone: string;
  email: string;
  role: string;
}

const UserListComponent: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [size, setSize] = useState(10);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedUsers, setSelectedUsers] = useState<User[]>([]);
  const [editedRole, setEditedRole] = useState<string>("선택하세요");
  const [forcingUpdate, setForcingUpdate] = useState(false);

  const fetchData = useCallback(async () => {
    try {
      setLoading(true);
      const response = await axios.get(`/admin-service/api/v1/get/users?page=${currentPage - 1}&size=${size}`);
      setUsers(response.data.content);
    } catch (error) {
      console.error('사용자 정보를 가져오는 중 오류 발생:', error);
      setError('사용자 정보를 가져오는 중 오류가 발생했습니다.');
    } finally {
      setLoading(false);
    }
  }, [currentPage, size]);

  useEffect(() => {
    fetchData();
    console.log(editedRole); 
  }, [fetchData]);

  const toggleUserSelection = (user: User) => {
    if (selectedUsers.includes(user)) {
      setSelectedUsers(selectedUsers.filter((selectedUser) => selectedUser !== user));
    } else {
      setSelectedUsers([...selectedUsers, user]);
    }
  };

  const resetSelectedUsers = () => {
    setSelectedUsers([]);
  };

  const handleUpdateUser = async () => {
    console.log(editedRole)
    try {
      await Promise.all(selectedUsers.map(async (user) => {
        await axios.put('/admin-service/api/v1/change-role', {
          uuid: user.uuid,
          role: editedRole,
        });
      }));

      fetchData();
      setEditedRole('');
      resetSelectedUsers(); // KoreanPlz: 선택된 사용자를 수정한 후 초기화
    } catch (error) {
      console.error('사용자 정보 수정 중 오류 발생:', error);
      setError('사용자 정보 수정 중 오류가 발생했습니다.');
    }
  };

  const handleForceUpdate = async (uuid: string) => {
    try {
      setForcingUpdate(true);
      await axios.put(`/admin-service/api/v1/updateIsForced/${uuid}`);
      // 선택한 사용자에 대한 성공적인 강제 업데이트 후에 사용자 목록을 업데이트하거나 다른 작업을 수행할 수 있습니다.
      resetSelectedUsers(); // KoreanPlz: 강제 업데이트 후 초기화
    } catch (error) {
      console.error('강제 탈퇴 중 오류 발생:', error);
      setError('강제 탈퇴 중 오류가 발생했습니다.');
    } finally {
      setForcingUpdate(false);
    }
  };

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  return (
    <div>
      <h2>사용자 목록</h2>
      {error ? (
        <p>Error: {error}</p>
      ) : loading ? (
        <p>Loading...</p>
      ) : (
        <div>
          <ul>
            {users.map((user) => (
              <li key={user.uuid}>
                <input
                  type="checkbox"
                  checked={selectedUsers.includes(user)}
                  onChange={() => toggleUserSelection(user)}
                />
                {user.name} | {user.email} | {user.nickname} | {user.phone}
              </li>
            ))}
          </ul>
          <div>
            <button onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
              이전 페이지
            </button>
            <span>{currentPage}</span>
            <button onClick={() => handlePageChange(currentPage + 1)}>다음 페이지</button>
          </div>
          <div>
        <h3>선택된 사용자 수정</h3>
        <label>
          역할:
          <select
            value={editedRole}
            onChange={(e) => {
              setEditedRole(e.target.value)
              console.log(editedRole);
              console.log(e.target.value);
            }
            }
          >
            <option value="">선택하세요</option>
            <option value="USER">USER</option>
            <option value="ADMIN">ADMIN</option>
          </select>
        </label>
        <button onClick={handleUpdateUser}>저장</button>
        </div>
  
          <div>
            <button
              onClick={() => handleForceUpdate(selectedUsers[0]?.uuid)}
              disabled={selectedUsers.length !== 1 || forcingUpdate}
            >
              {forcingUpdate ? '강제 탈퇴 중...' : '강제 탈퇴'}
            </button>
          </div>
          <div>
            <button
              onClick={() => handleForceUpdate(selectedUsers[0]?.uuid)}
              disabled={selectedUsers.length !== 1 || forcingUpdate}
            >
              {forcingUpdate ? '강제 탈퇴 중...' : '강제 탈퇴 해제'}
            </button>
          </div>
        </div>
      )}
    </div>
  );
};

export default UserListComponent;