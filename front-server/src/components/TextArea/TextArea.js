 import "./style.css";

const TextArea = ({setTitle, setPrice, setDetail, 
    title, price, detail }) => {
    
  return (
    <div className="textArea-wrapper">
      <input
        onChange={(e) => {
          setTitle(e.target.value);
        }}
        className="title"
        placeholder="제목"
        value={title}
      />
      <input
        onChange={(e) => {
          setPrice(e.target.value);
        }}
        className="price"
        placeholder="가격"
        value={price}
      />
      <textarea
       rows="12" cols="52"
        onChange={(e) => {
          setDetail(e.target.value);
        }}
        className="text"
        placeholder="상세 정보"
        value={detail}
      />
    </div>
  );
};
export default TextArea;