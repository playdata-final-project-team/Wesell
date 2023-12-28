import { Pageable } from 'types/interface';
import ResponseDto from '../response.dto';
import MyPostlListResponseDto from './my.deal-list.response.dto';
import { Serializable } from 'child_process';
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
