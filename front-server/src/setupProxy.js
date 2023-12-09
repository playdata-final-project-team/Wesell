/* eslint-disable no-undef */
/* eslint-disable @typescript-eslint/no-var-requires */
const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function (app) {
  app.use(
    '/auth-server',
    createProxyMiddleware({
      //proxy가 필요한 path parameter
      target: 'http://localhost:8000', //타겟이 되는 api url
      changeOrigin: true, // 서버 구성에 따른 호스트 헤더 변경 여부 설정
    }),
  );

  app.use(
    '/user-service',
    createProxyMiddleware({
      //proxy가 필요한 path parameter
      target: 'http://localhost:8000', //타겟이 되는 api url
      changeOrigin: true, // 서버 구성에 따른 호스트 헤더 변경 여부 설정
    }),
  );

  app.use(
    '/deal-service',
    createProxyMiddleware({
      //proxy가 필요한 path parameter
      target: 'http://localhost:8000', //타겟이 되는 api url
      changeOrigin: true, // 서버 구성에 따른 호스트 헤더 변경 여부 설정
    }),
  );

  app.use(
    '/admin-service',
    createProxyMiddleware({
      //proxy가 필요한 path parameter
      target: 'http://localhost:8000', //타겟이 되는 api url
      changeOrigin: true, // 서버 구성에 따른 호스트 헤더 변경 여부 설정
    }),
  );
};
