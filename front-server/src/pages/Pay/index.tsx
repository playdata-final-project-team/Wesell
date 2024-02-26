import { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface Props {
  payId : number;
}
const Payment = (props : Props) => {

  const {payId} = props;
  const navigate = useNavigate();
  //결제 요청
  useEffect(() => {
    const jquery = document.createElement("script");
    jquery.src = "http://code.jquery.com/jquery-1.12.4.min.js";
    const iamport = document.createElement("script");
    iamport.src = "http://cdn.iamport.kr/js/iamport.payment-1.1.7.js";
    document.head.appendChild(jquery);
    document.head.appendChild(iamport);
    return () => {
      document.head.removeChild(jquery);
      document.head.removeChild(iamport);
    };
  }, []);

  const onClickPayment = () => {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const { IMP } : any = window;
    IMP.init('imp15354308'); //가맹점 식별코드
    
    const data = {
            pg: 'kakaopay',
            pay_method: 'cash',
            merchant_uid: `mid_${new Date().getTime()}`,
            name: '결제 테스트',
            amount: 1000,
            custom_data: {
                name: '부가정보',
                desc: '세부 부가정보',
            },
        };

        IMP.request_pay(data, callback);
  };

  const callback = (response: { success: any; error_msg: any; imp_uid: any; merchant_uid: any; pay_method: any; paid_amount: any; status: any; }) => {
    const { success, error_msg } = response;

    if (success) {
        alert('결제 성공');
        navigate('/payResult/'+ payId);
    } else {
        alert(`결제 실패: ${error_msg}`);
    }
};

  return (
    <div>
      <button className="payButton" onClick={onClickPayment}>결제하기</button>
    </div>
  );
};

export default Payment;