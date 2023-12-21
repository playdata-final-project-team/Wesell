import pageable from 'types/pageable';
import ResponseDto from '../response.dto';
import MyDealListResponseDto from './my.deal-list.response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface MyDealListWithPageResponseDto extends ResponseDto {
  content: MyDealListResponseDto[];
  pageable: pageable | string;
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
  