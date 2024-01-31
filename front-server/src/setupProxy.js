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

  app.use(
    '/chat-service',
    createProxyMiddleware({
      target: 'http://localhost:8000',
      changeOrigin: true,
    }),
  );

  app.use(
    '/image-server',
    createProxyMiddleware({
      target: 'http://localhost:8000', 
      changeOrigin: true, 
    }),
  );

  app.use(
    '/pay-service',
    createProxyMiddleware({
      target: 'http://localhost:8000', 
      changeOrigin: true, 
    }),
  );

    app.use('/web-push',
        createProxyMiddleware({
            target: 'http://localhost:3333', //타겟이 되는 api url
            changeOrigin: true, // 서버 구성에 따른 호스트 헤더 변경 여부 설정
            pathRewrite: {
                '^/web-push': '', // 경로에서 '/web-push'를 제거합니다.
            },
        }),
    );

};
