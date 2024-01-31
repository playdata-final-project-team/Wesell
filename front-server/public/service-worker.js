"use strict";
/// <reference lib="webworker" />
function log(...args) {
    console.log('service-worker:', ...args);
}
// https://github.com/microsoft/TypeScript/issues/14877
((self) => {
    self.addEventListener('install', (event) => {
        log('install', { event });
        event.waitUntil(self.skipWaiting());
    });
    self.addEventListener('activate', (event) => {
        log('activate', { event });
    });
    self.addEventListener('push', (event) => {
        var _a;
        log('push', { event });
        const message = (_a = event.data) === null || _a === void 0 ? void 0 : _a.json();
        event.waitUntil(self.registration.showNotification(message.title, {
            body: message.body,
        }));
    });
    self.addEventListener('notificationclick', (event) => {
        log('notificationclick', { event });
        self.clients.openWindow('https://github.com/leegeunhyeok/web-push');
    });
})(self);
