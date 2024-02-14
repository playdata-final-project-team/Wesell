import TextEditor from 'components/TextEditor';
import './style.css';
import { useRef, useState } from 'react';

const PostWrite = () => {
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  const quillRef: any = useRef();
  const [htmlContent, setHtmlContent] = useState<string>('');

  return (
    <div className="post-write-wrapper">
      <div className="post-write-container">
        <h2 className="board-title">게시판 명</h2>
        <div className="post-write-title">
          <input placeholder="제목을 입력해주세요." />
        </div>
        <div className="post-write-content">
          <TextEditor
            quillRef={quillRef}
            htmlContent={htmlContent}
            setHtmlContent={setHtmlContent}
          ></TextEditor>
        </div>
        <div className="post-write-btns">
          <button className="post-write-cancel-btn"> 취소 </button>
          <button className="post-write-ok-btn"> 등록 </button>
        </div>
      </div>
    </div>
  );
};

export default PostWrite;
