import { ResponseCode } from 'types/enum';

export default interface ResponseDto {
  time: Date;
  status: string;
  code: ResponseCode;
  message: string;
  vfMessages?: string[];
}
