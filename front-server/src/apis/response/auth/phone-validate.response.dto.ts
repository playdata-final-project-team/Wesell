import ResponseDto from '../response.dto';

// eslint-disable-next-line @typescript-eslint/no-empty-interface
export default interface PhoneValidateResponseDto extends ResponseDto {
  content: {
    uuid: string;
    certNum: string;
  };
}
