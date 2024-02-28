export default interface Comment {
  id: number;
  writer: string;
  content: string;
  createdAt: string;
  children: Comment[];
}
