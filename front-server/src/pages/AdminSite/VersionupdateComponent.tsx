import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const VersionUpdateComponent = () => {
  const navigate = useNavigate();
  const [jsVersion, setJsVersion] = useState('1.0');
  const [cssVersion, setCssVersion] = useState('1.0');
  const [title, setTitle] = useState('Default Title');
  const [agree, setAgree] = useState('개인정보 제공에 동의하십니까?');

  const handleSave = async () => {
    try {

      console.log(jsVersion); // 22

      

      // 사용자가 입력한 값을 서버로 전송
      const response = await axios.post('/admin-service/api/v1/version', null, { 
        params :{
        jsVersion: jsVersion,
        cssVersion: cssVersion,
        title: title,
        agree: agree,
      }});

      // 서버에서의 응답 데이터 처리
      const responseData = response.data;
      console.log('서버 응답 데이터:', responseData); // 1.0

      // 수정 완료 후 페이지 이동 및 값 전달
      if(response.status === 200) {
      navigate('/testJiho7', {
        state: { updatedData: responseData },
      });
    }
    } catch (error) {
      console.error('서버 통신 중 오류 발생:', error);
    }
  };


  return (
    <div>
      <h1>프로젝트 버전 정보</h1>
      <label>
        JavaScript 버전:
        <input
          type="text"
          value={jsVersion}
          onChange={(e) => setJsVersion(e.target.value)}
        />
      </label>
      <br />
      <label>
        CSS 버전:
        <input
          type="text"
          value={cssVersion}
          onChange={(e) => setCssVersion(e.target.value)}
        />
      </label>
      <br />
      <label>
        프로젝트 제목:
        <input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </label>
      <br />
      <label>
        개인정보 동의 여부:
        <input
          type="text"
          value={agree}
          onChange={(e) => setAgree(e.target.value)}
        />
      </label>
      <br />
      <button type="button" onClick={handleSave}>
        저장 및 페이지 이동
      </button>
    </div>
  );
};

export default VersionUpdateComponent;