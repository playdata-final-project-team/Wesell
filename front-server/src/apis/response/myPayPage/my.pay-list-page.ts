export default interface PayPageResponseDto {
    payId: number;
    deliveryId: number;
    title: string; 
    date: string; //구매 날짜
    status: string; //배송 현황
}
