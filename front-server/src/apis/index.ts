import axios from "axios";
import { SignInRequestDto, SignUpRequestDto } from "./request/auth";

const DOMAIN = 'http://localhost:3000';

const API_DOMAIN = `${DOMAIN}/api/v1`;

const SIGN_IN_URL = `${API_DOMAIN}/auth-server/sign-in`;

const SIGN_UP_URL = `${API_DOMAIN}/auth-server/sign-up`;

export const signInRequest = async (requestBody: SignInRequestDto) => {
    const result = await axios.post(SIGN_IN_URL, requestBody)
        .then(response =>{
            // const  { } = response.data;
        }) // Sign-in 요청
}