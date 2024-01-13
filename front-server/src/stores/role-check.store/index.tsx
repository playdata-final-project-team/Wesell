import { UserInfo } from 'types/interface';
import { create } from 'zustand';

type State = {
  userInfo: UserInfo;
};

type Action = {
  setUserInfo: (userInfo: UserInfo) => void;
};

const useRoleCheckStore = create<State & Action>((set) => ({
  userInfo: {
    uuid: '',
    role: '',
  },
  setUserInfo: (userInfo) => set({ userInfo }),
}));

export default useRoleCheckStore;
