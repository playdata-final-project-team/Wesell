import React, { useEffect, useState, useCallback } from 'react';
import axios from 'axios';
import styled from 'styled-components';

interface User {
  uuid: string;
  nickname: string;
  name: string;
  phone: string;
  email: string;
  role: string;
}

// Styled Components
const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;

const PaginationButton = styled.button`
  border: none;
  background-color: #3498db;
  color: #ffffff;
  border-radius: 50%;
  width: 100px;
  height: 30px;
  margin: 0 5px;
  cursor: pointer;
`;

const HeaderRow = styled.div`
  display: flex;
  font-weight: bold;
`;

const ListItem = styled.li`
  display: flex;
  align-items: center;
`;

const Name = styled.div`
  flex: 3;
  display: flex;
  align-items: center;
`;

const NameText = styled.div`
  margin-left: 70px;
`;

const Role = styled.div`
  flex: 2;
  margin-left: 20px;
`;

const PaginationControls = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
`;

const UserModificationControls = styled.div`
  margin-top: 20px;
  width: 100%;
`;

const Checkbox = styled.input`
  margin-left: -40px;
`;

const Button = styled.button`
  margin-right: 10px;
`;

const UserListComponent: React.FC = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [size, setSize] = useState(10);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedUsers, setSelectedUsers] = useState<User[]>([]);
  const [editedRole, setEditedRole] = useState<string>('선택하세요');
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
    try {
      await Promise.all(
        selectedUsers.map(async (user) => {
          await axios.put('/admin-service/api/v1/change-role', {
            uuid: user.uuid,
            role: editedRole,
          });
        })
      );

      fetchData();
      setEditedRole('');
      resetSelectedUsers();
    } catch (error) {
      console.error('사용자 정보 수정 중 오류 발생:', error);
      setError('사용자 정보 수정 중 오류가 발생했습니다.');
    }
  };

  const handleForceUpdate = async (uuid: string, isForced: boolean) => {
    try {
      setForcingUpdate(true);
      const endpoint = isForced ? 'updateIsForced' : 'updateIsNotForced';
      await axios.put(`/admin-service/api/v1/${endpoint}/${uuid}`);
      resetSelectedUsers();
    } catch (error) {
      console.error(`강제 탈퇴 ${isForced ? '' : '해제'} 중 오류 발생:`, error);
      setError(`강제 탈퇴 ${isForced ? '' : '해제'} 중 오류가 발생했습니다.`);
    } finally {
      setForcingUpdate(false);
    }
  };

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  return (
    <Container>
      <h2>사용자 목록</h2>
      {error ? (
        <p>Error: {error}</p>
      ) : loading ? (
        <p>Loading...</p>
      ) : (
        <div style={{ width: '60%', marginTop: '20px' }}>
          {/* Header Row */}
          <HeaderRow>
            <div style={{ flex: 1 }}>
              <span>선택</span>
            </div>
            <div style={{ flex: 3 }}>
              <span>이름</span>
            </div>
            <div style={{ flex: 3 }}>
              <span>이메일</span>
            </div>
            <div style={{ flex: 2 }}>
              <span>닉네임</span>
            </div>
            <div style={{ flex: 2 }}>
              <span>번호</span>
            </div>
            <Role>
              <span>역할</span>
            </Role>
          </HeaderRow>

          {/* User List */}
          <ul>
            {users.map((user) => (
              <ListItem key={user.uuid}>
                <Checkbox
                  type="checkbox"
                  checked={selectedUsers.includes(user)}
                  onChange={() => toggleUserSelection(user)}
                />
                <Name>
                  <NameText>{user.name}</NameText>
                </Name>
                <div style={{ flex: 3 }}>{user.email}</div>
                <div style={{ flex: 2 }}>{user.nickname}</div>
                <div style={{ flex: 2 }}>{user.phone}</div>
                <Role>
                  <span>{user.role}</span>
                </Role>
              </ListItem>
            ))}
          </ul>

          {/* Pagination and User Modification Controls */}
          <PaginationControls>
            <PaginationButton onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
              이전 페이지
            </PaginationButton>
            <span>{currentPage}</span>
            <PaginationButton onClick={() => handlePageChange(currentPage + 1)}>다음 페이지</PaginationButton>
          </PaginationControls>

          <UserModificationControls>
            <h3>선택된 사용자 수정</h3>
            <label>
              역할:
              <select
                value={editedRole}
                onChange={(e) => setEditedRole(e.target.value)}
              >
                <option value="">선택하세요</option>
                <option value="USER">USER</option>
                <option value="ADMIN">ADMIN</option>
              </select>
            </label>
            <button onClick={handleUpdateUser}>저장</button>
          </UserModificationControls>

          {/* Force Update Controls */}
          <UserModificationControls>
            <Button
              onClick={() => handleForceUpdate(selectedUsers[0]?.uuid, true)}
              disabled={selectedUsers.length !== 1 || forcingUpdate}
            >
              {forcingUpdate ? '강제 탈퇴 중...' : '강제 탈퇴'}
            </Button>
            <Button
              onClick={() => handleForceUpdate(selectedUsers[0]?.uuid, false)}
              disabled={selectedUsers.length !== 1 || forcingUpdate}
            >
              {forcingUpdate ? '강제 탈퇴 중...' : '강제 탈퇴 해제'}
            </Button>
          </UserModificationControls>
        </div>
      )}
    </Container>
  );
};

export default UserListComponent;