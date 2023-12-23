import { Pageable } from 'types/interface';
import ResponseDto from '../response.dto';
import MyDealListResponseDto from './my.deal-list.response.dto';
export default interface MyDealListWithPageResponseDto extends ResponseDto {
  content: MyDealListResponseDto[];
  pageable: Pageable | string;
  last: boolean;
  totalPages: number;
  totalElements: number;
  size: number;
  number: number;
  sort: [];
  first: boolean;
  numberOfElements: number;
  empty: boolean;
}
