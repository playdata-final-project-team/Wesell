import { create } from 'zustand';

type State = {
  certNum: string;
};

type Action = {
  setCertNum: (certNum: string) => void;
};

const certNumStore = create<State & Action>((set) => ({
  certNum: '',
  setCertNum: (certNum) => set({ certNum }),
}));

export default certNumStore;
