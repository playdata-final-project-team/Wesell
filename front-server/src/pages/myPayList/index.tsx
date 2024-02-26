import ABox from 'components/aBox';
import './style.css';
import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { ResponseDto } from 'apis/response';
import ListPagenation from 'components/Pagenation';
import ResponseCode from 'constant/response-code.enum';
import { BsFileExcelFill } from 'react-icons/bs';
import PayPageResponseDto from 'apis/response/myPayPage/my.pay-list-page';

// component: 마이페이지 컴포넌트 //

import CommonTable from 'components/table/CommonTable';
import CommonTableColumn from 'components/table/CommonTableColumn';
import CommonTableRow from 'components/table/CommonTableRow';
import axios from 'axios';

function GetData() {
  const [data, setData] = useState({});
  const uuid = window.sessionStorage.getItem("uuid");

  useEffect(() => {
    axios.get('/pay-service/api/v2/mypage/payment', {
      params: {
        uuid: uuid 
      }
    }).then((response)=> {
      setData(response.data);
    })
  }, []);

  const item = (Object.values(data)).map((item : any) => (
    <CommonTableRow key={item.payId}>
      <CommonTableColumn>{item.title}</CommonTableColumn>
      <CommonTableColumn>{item.date}</CommonTableColumn>
      <CommonTableColumn>{item.status}</CommonTableColumn>
    </CommonTableRow>
  ));

  return item;
}

function MyPayList() {
  const item = GetData();

  return (<>
    <CommonTable headersName={['글번호', '제목', '등록일', '작성자']}>
      {item}
    </CommonTable>
  </>);
}
  
export default MyPayList;
