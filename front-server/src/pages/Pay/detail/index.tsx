import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import './index.css';

interface PayJson {
    orderNumber:string;
    amount:number;
}
interface DeliveryJson {
    receiver:string;
    address:string;
    status:string;
}


function PayResultPage() {
    const {payId} = useParams();
    const [pay, setPay] = useState<PayJson | null>(null);
    const [delivery, setDelivery] = useState<DeliveryJson | null>(null);

    useEffect(() => {
        const RESULT_URL = `/pay-service/api/v2/payment?payId=${payId}`;
        axios.get(RESULT_URL)
        .then((response) => setPay(response.data['payDto']))

        axios.get(RESULT_URL)
        .then((response) => setDelivery(response.data['deliveryDto']))
        console.log(delivery);
        console.log(payId);
    },[]);

    return(
        <>
      <div className="pay-wrapper">
  <div className="pay-body">
    {
      pay && delivery && (
        <div className="pay-content">
          <div className="pay-box">
          <p className="orderNumber"  style={{ fontWeight: 'bold' }}>주문번호</p>
          <p>{pay.orderNumber}</p>
          </div>
          <div className="pay-box">
            <p className="receiver"  style={{ fontWeight: 'bold' }}>수령인</p>
            <p>{delivery.receiver}</p>
          </div>
          <div className="pay-box">
            <p className="address"  style={{ fontWeight: 'bold' }}>주소</p>
            <p>{delivery.address}</p>
          </div>
          <div className="pay-box">
            <p className="orderNumber"  style={{ fontWeight: 'bold' }}>주문번호</p>
            <p>{pay.orderNumber}</p>
          </div>
          <div className="pay-box">
            <p className="amount"  style={{ fontWeight: 'bold' }}>결제 금액</p>
            <p>{pay.amount}</p>
          </div>
          <div className="pay-box">
            <p className="shipping-status" style={{ fontWeight: 'bold' }}>배송 상태</p>
            <p>{delivery.status}</p>
          </div>
        </div>
      )
    }
  </div>
</div>

        </>
    )
}


export default PayResultPage;