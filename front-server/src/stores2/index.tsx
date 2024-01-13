
import { create } from 'zustand';

type State = {
    postId: number;
}

type Action = {
    setPostId: (postId: State['postId']) => void
}

const useStore = create<State&Action>(set => ({
    postId: 0,
    setPostId: (data: number) => set({ postId: data}),
  }))

  export default useStore;