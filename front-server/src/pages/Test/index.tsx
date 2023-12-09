import axios from 'axios';
import ButtonBox from 'components/ButtonBox';
import {useState} from 'react';

const CorsTest: React.FC = () => {

  const [message, setMessage] = useState('');

  const responseHandler = (data : string) => {
    setMessage(data);
    console.log(message);
    return data;
  };

  const errorHandler = (message: string) => {
    setMessage(message);
    console.log(message);
    return message;
  };

  const notCorsResponse = async () => {
    try{
      const response = await axios.get('http://localhost:8081/not-cors')
      const responseBody: string = response.data;
      responseHandler(responseBody);
    }catch(error){
      errorHandler("오류발생");
    }
  };

  const corsResponse = async () => {
    try{
      const response = await axios.get('http://localhost:8081/cors')
      const responseBody: string = response.data;
      responseHandler(responseBody);
    }catch(error){
      errorHandler("오류발생");
    }
  };

  const notProxyResponse = async () => {
    try{
      const response = await axios.get('/not-proxy')
      const responseBody: string = response.data;
      responseHandler(responseBody);
    }catch(error){
      errorHandler("오류발생");
    }
  };

  const proxyResponse = async () => {
    try{
      const response = await axios.get('/proxy')
      const responseBody: string = response.data;
      responseHandler(responseBody);
    }catch(error){
      errorHandler("오류발생");
    }
  };

  return (
    <>
      <ButtonBox isEnable={true} label="not-cors" type="button" onClick={notCorsResponse} />
      <ButtonBox isEnable={true} label="cors" type="button" onClick={corsResponse} />
      <ButtonBox isEnable={true} label="not-proxy" type="button" onClick={notProxyResponse} />
      <ButtonBox isEnable={true} label="proxy" type="button" onClick={proxyResponse} />
    </>
  );
};

export default CorsTest;
