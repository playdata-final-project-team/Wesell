import ResponseDto from "../response.dto";

export default interface GetSignInUserResponseDto extends ResponseDto{
    uuid: string;
    role: string;
}