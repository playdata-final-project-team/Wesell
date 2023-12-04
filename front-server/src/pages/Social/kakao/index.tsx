import { useEffect } from 'react';
import { kakaoCallbackAuthCodeRequest } from "apis";
import Loading from "components/Loading";
import { useLocation } from "react-router-dom";

const Social: React.FC = ()=>{
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);
    const authCode = queryParams.get('code');

    useEffect(
        ()=>{
       kakaoCallbackAuthCodeRequest(authCode);
    },[]);
    
    return(<Loading />);
};

export default Social;