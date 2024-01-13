 import "./style.css";

const TextArea = ({setTitle, setPrice, setDetail, setLink, 
    title, price, detail, link }) => {
    
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
      <input
        onChange={(e) => {
          setLink(e.target.value);
        }}
        className="link"
        placeholder="오픈 카카오톡 링크"
        value={link}
      />
    </div>
  );
};
export default TextArea;