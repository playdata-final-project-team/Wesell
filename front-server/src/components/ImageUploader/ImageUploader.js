import './ImageUploader.css';
import { Button } from '@mui/material';

const ImageUploader = ({ preview_URL, setImage }) => {
  let inputRef;

  const saveImage = (e) => {
    e.preventDefault();
    const fileReader = new FileReader();
    if (e.target.files[0]) {
      fileReader.readAsDataURL(e.target.files[0]);
    }
    fileReader.onload = () => {
      setImage({
        image_file: e.target.files[0],
        preview_URL: fileReader.result,
      });
    };
  };

  return (
    <div className="uploader-wrapper">
      <Button className="disable-button" variant="outlined" size="large">
        사진과 내용을 모두 입력하세요.
      </Button>
      <input
        type="file"
        accept="image/*"
        onChange={saveImage}
        ref={(refParam) => (inputRef = refParam)}
        style={{ display: 'none' }}
      />
      <div className="img-wrapper">
        <img src={preview_URL} />
      </div>
      <div className="upload-button">
        <Button variant="outlined" color="primary" onClick={() => inputRef.click()}>
          😎사진 고르기😎
        </Button>
      </div>
    </div>
  );
};

export default ImageUploader;
