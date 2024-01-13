import ResponseDto from '../response.dto';
import MyPostlListResponseDto from './my.deal-list.response.dto';
export default interface PageResponseDto extends ResponseDto {
  dtoList: MyPostlListResponseDto[];
  page: number;
  totalElements: number;
  size: number;
  // pageable: Pageable | string;
  // last: boolean;
  // totalPages: number;
  // totalElements: number;
  // size: number;
  // number: number;
  // sort: [];
  // first: boolean;
  // numberOfElements: number;
  // empty: boolean;
}
