import axios from "axios";
import ResponseDto from "../response.dto";
import PayPageResponseDto from "./my.pay-list-page";



// api request : 마이페이지 - 나의 판매 내역 요청
export const myPayListRequest = async (uuid: string | null, page: number) => {
const MY_PAY_LIST_URL = () => `/pay-service/api/v2/mypage/payment?id=${uuid}`;
  try {
    const response = await axios.get(MY_PAY_LIST_URL(), {
      params: { uuid: uuid, page: page },
    });
    const responseBody: PayPageResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};

// api request : 마이페이지 - 구매 내역 삭제 요청
export const deletePayListRequest = async (idArr: number[]) => {
  const DELETE_PAYLIST_URL = () => `/pay-service/api/v2/checked/delete`;
    try {
      const response = await axios.put(DELETE_PAYLIST_URL(), idArr);
      const responseBody: ResponseDto = response.data;
      return responseBody;
      // eslint-disable-next-line @typescript-eslint/no-explicit-any
    } catch (error: any) {
      if (!error.response) return null;
      const responseBody: ResponseDto = error.response.data;
      return responseBody;
    }
  };


  // api request : 마이페이지 - 배송지 수정 요청 
export const shippingStatusChangeRequest = async (deliveryId: number) => {
  const SHIPPINGSTATUS_CHANGE_URL = () => `/pay-service/api/v2/delivery/finish?id=${deliveryId}`;
  try {
    const response = await axios.put(SHIPPINGSTATUS_CHANGE_URL(), deliveryId);
    const responseBody: ResponseDto = response.data;
    return responseBody;
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
  } catch (error: any) {
    if (!error.response) return null;
    const responseBody: ResponseDto = error.response.data;
    return responseBody;
  }
};
