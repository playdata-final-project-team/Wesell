import axios from "axios";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

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
                            <p className="orderNumber">주문번호 {pay.orderNumber}</p>
                            <p className="receiver">수령인 {delivery.receiver}</p>
                            <p className="addres">주소 {delivery.address}</p>
                            <p className="orderNumber">주문번호 {pay.orderNumber}</p>
                            <p className="amount">결제 금액 {pay.amount}</p>
                            <p className="shipping-status">배송 상태 {delivery.status}</p>
                        </div>
                    )
                }
            </div>
        </div>
        </>
    )
}


export default PayResultPage;