import { useParams, useNavigate } from 'react-router-dom';
import { useCallback, useEffect, useState } from 'react';
import PayTextArea from 'components/payTextArea';
import { toast } from 'react-toastify';
import axios from 'axios';
import Payment from '..';
import './index.css';

function RequestPay() {
  const { postId } = useParams();
  const [payId, setPayId] = useState<number|null>(null);
  const [ address, setAddress] = useState('');
  const [ receiver, setReceiver] = useState('');
  const onClickBeforePay = useCallback(async () => {
    try{
        const deliveryDto = {
            receiver: receiver,
            address: address
          };
        const deliveryResponse = await axios.post("/pay-service/api/v2/delivery", deliveryDto );
        const data1 = deliveryResponse.data;
        console.log(data1);
        console.log("delivery"+data1);

        const payDto = {
          deliveryId:data1,
          buyer:window.sessionStorage.getItem("uuid"),
          // buyer: "wid5",
          productId:postId,
          type:0,
        };
        const payResponse = await axios.post("/pay-service/api/v2/payment", payDto);
        console.log(payResponse);
        const payId = payResponse.data;
        setPayId(payId);
        console.log("pay"+payId);
    } catch (e) {
      toast.error("입력을 확인해주세요!",{
      position: "top-center",
      });
    }
  }, [receiver, address]);
    

    return(
        <div className="pay-wrapper">
            <div className="pay-body">
              <PayTextArea setReciever={setReceiver} setAddress={setAddress} receiver={receiver} address={address}/>
            </div>
            <div className="submitButton">
              <button className="successButton" onClick={onClickBeforePay}>배송지 확인</button>
              {payId && <Payment payId={payId}/>}</div>
            </div>
    );
}

export default RequestPay;