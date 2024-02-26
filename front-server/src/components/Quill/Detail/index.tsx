import { Box } from '@mui/material';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';

interface Props {
  // useRef를 사용하여 생성된 ref 객체가 되어야 하기 때문에, MutableRefObject 타입이다.
  htmlContent: string;
}

// eslint-disable-next-line react/display-name
const TextEditor = (props: Props) => {
  const { htmlContent } = props;
  return (
    <Box>
      <ReactQuill
        readOnly={true}
        value={htmlContent}
        theme="bubble"
        style={{
          border: 'solid 1px #c0c0c0',
          height: '400px',
          margin: '10px 0px 50px 0px',
          padding: '10px',
        }} // style
      />
    </Box>
  );
};

export default TextEditor;
