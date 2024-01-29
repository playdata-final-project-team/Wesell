import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

interface PayJson {
    orderNumber:string;
    receiver:string;
    address:string;
    amount:number;
    shipStatus:string;
}

function PayResultPage() {
    const {payId} = useParams();
    const [pay, setPay] = useState<PayJson | null>(null);

    useEffect(() => {
        const RESULT_URL = `pay-service/api/v2/payment?id=${payId}`;
        fetch(RESULT_URL,{
            method:'GET',
        })
        .then((response) => response.json())
      .then((json) => setPay(json));
    },[]);

    return(
        <>
        <div className="pay-wrapper">
            <div className="pay-body">
                {
                    pay && (
                        <div className="pay-content">
                            <p className="orderNumber">주문번호 {pay.orderNumber}</p>
                            <p className="receiver">수령인 {pay.receiver}</p>
                            <p className="addres">주소 {pay.address}</p>
                            <p className="orderNumber">주문번호 {pay.orderNumber}</p>
                            <p className="amount">결제 금액 {pay.amount}</p>
                            <p className="shipping-status">배송 상태 {pay.shipStatus}</p>
                        </div>
                    )
                }
            </div>
        </div>
        </>
    )
}


export default PayResultPage;