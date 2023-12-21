import { create} from 'zustand';

type State = {
    uuid: string | null
    role: string | null
}

type Action = {
    setUuid: (uuid: State['uuid']) => void
    setRole: (role: State['role']) => void
}

const useStore = create<State&Action>(set => ({
    uuid: window.sessionStorage.getItem('uuid'),
    setUuid : (uuid) => set({uuid: uuid}),
    role: window.sessionStorage.getItem('role'),
    setRole : (role) => set({role: role}),
}));

export default useStore;