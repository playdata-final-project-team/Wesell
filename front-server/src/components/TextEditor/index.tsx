import { Box } from '@mui/material';
import { MutableRefObject, memo, useMemo } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

interface Props {
  // useRef를 사용하여 생성된 ref 객체가 되어야 하기 때문에, MutableRefObject 타입이다.
  quillRef: MutableRefObject<ReactQuill | null>;
  htmlContent: string;
  setHtmlContent: (content: string) => void;
}

// eslint-disable-next-line react/display-name
const TextEditor = memo((props: Props) => {
  const { quillRef, htmlContent, setHtmlContent } = props;
  const modules = useMemo(
    () => ({
      // 툴바에 담을 기능들
      toolbar: {
        container: [
          ['bold', 'italic', 'underline', 'strike', 'blockquote'],
          [{ size: ['small', false, 'large', 'huge'] }, { color: [] }],
          [
            { list: 'ordered' },
            { list: 'bullet' },
            { indent: '-1' },
            { indent: '+1' },
            { align: [] },
          ],
        ],
      },
    }),
    [],
  );
  return (
    <Box>
      <ReactQuill
        // ref={quillRef}
        ref={(element) => {
          if (element !== null) {
            quillRef.current = element;
          }
        }}
        value={htmlContent}
        onChange={setHtmlContent}
        modules={modules}
        theme="snow"
        style={{
          height: '400px',
          marginBottom: '10%',
        }} // style
      />
    </Box>
  );
});

export default TextEditor;
