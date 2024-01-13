
const PayTextArea = ({setReciever, setAddress, receiver, addres}) => {
    
  return (
    <div className="textArea-wrapper">
      <input
        onChange={(e) => {
            setReciever(e.target.value);
        }}
        className="receiver"
        placeholder="수취인"
        value={receiver}
      />
      <input
        onChange={(e) => {
            setAddress(e.target.value);
        }}
        className="addres"
        placeholder="배송지"
        value={addres}
      />
    </div>
  );
};

export default PayTextArea;