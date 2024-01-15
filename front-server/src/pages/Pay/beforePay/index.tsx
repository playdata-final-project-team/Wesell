import { useParams, useNavigate } from 'react-router-dom';
import { useCallback, useEffect, useState } from 'react';
import PayTextArea from 'components/payTextArea';
import { toast } from 'react-toastify';
import axios from 'axios';
import Payment from '..';

function getAmount() {
    const[amount, setAmount] = useState();
    useEffect(() => {
      fetch("/deal-service/api/v1/price", {
        method:"GET"
      })
      .then((response) => response.json())
      .then((json) => setAmount(json))
    },[]);
  
    return amount;
  }

function RequestPay() {

  const { postId } = useParams();
  const navigate = useNavigate();
  const [ address, setAddress] = useState('');
  const [ receiver, setReceiver] = useState('');
  const onClickBeforePay = useCallback(async () => {
    try{
        const deliveryDto = {
            receiver:receiver,
            address: address};
        const requestDto = new Blob([JSON.stringify(deliveryDto)]);
        const deliveryResponse = await axios.post("/pay-service/api/v2/delivery", requestDto);
        const deliveryId = deliveryResponse.data;
        const payDto = {
          dliveryId:deliveryId,
          buyer:postId,
          productId:window.sessionStorage.getItem("uuid"),
          type:0,
        };
        const payResponse = await axios.post("/pay-service/api/v2/payment", payDto);
        const payId = payResponse.data;
        navigate('/payment/'+ payId);
    //   fetch("/pay-service/api/v2/payment",{
    //     method:"POST",
    //     headers:{
    //     "Content-Type":"application/json"
    //     },
    //     body:JSON.stringify({
    //     buyer:postId,
    //     productId:window.sessionStorage.getItem("uuid"),
    //     type:0,
    //     receiver:dto.receiver,
    //     address:dto.address
    //     })
    //   });
      
    } catch (e) {
      toast.error("입력을 확인해주세요!",{
      position: "top-center",
      });
    }
  }, []);
    
    return(
        <div className="pay-wrapper">
            <div className="pay-body">
                <PayTextArea setReciever={setReceiver} setAddress={setAddress} receiver={receiver} addres={address}/>
                <button onClick={onClickBeforePay}>배송지 확인</button>
            </div>
            <div className="pay-button">
                <button onClick={Payment}></button>
            </div>
        </div>
    );
}

export default RequestPay;