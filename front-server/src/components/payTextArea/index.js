import './index.css';

const PayTextArea = ({setReciever, setAddress, receiver, address}) => {
    
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
      <textarea
       rows="10" cols="22"
        onChange={(e) => {
          setAddress(e.target.value);
        }}
        className="address"
        placeholder="배송지"
        value={address}
      />
    </div>
  );
};

export default PayTextArea;