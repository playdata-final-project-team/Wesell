import React, { useEffect, useRef, useState } from 'react';

type Store = {
    pushSupport: boolean;
    serviceWorkerRegistration: ServiceWorkerRegistration | null;
    pushSubscription: PushSubscription | null;
};

type Elements = {
    currentUserId: HTMLParagraphElement | null;
    notificationPermission: HTMLParagraphElement | null;
    pushSupport: HTMLParagraphElement | null;
    registration: HTMLParagraphElement | null;
    subscription: HTMLPreElement | null;
    sendStatus: HTMLParagraphElement | null;
    message: HTMLInputElement | null;
    targetUserId: HTMLInputElement | null;
};

const IndexPage: React.FC = () => {
    const userId = localStorage.getItem('userId');
    const elements = useRef<Elements>({
        // Status text
        currentUserId: null,
        registration: null,
        pushSupport: null,
        notificationPermission: null,
        subscription: null,
        sendStatus: null,
        // Inputs
        message: null,
        targetUserId: null,
    });

    const [store, setStore] = useState<Store>({
        pushSupport: false,
        serviceWorkerRegistration: null,
        pushSubscription: null,
    });

    if (!userId) location.href = '/login.tsx';

    async function registerServiceWorker() {
        if (!('serviceWorker' in navigator)) return;

        try {
            let registration = await navigator.serviceWorker.getRegistration();
            if (!registration) {
                registration = await navigator.serviceWorker.register('/service-worker.js');
            }

            const pushManager = registration?.pushManager;
            const pushSubscription = pushManager ? await pushManager.getSubscription() : null;

            setStore((prevStore) => ({
                ...prevStore,
                serviceWorkerRegistration: registration ?? null,
                pushSupport: !!pushManager,
                pushSubscription: pushSubscription ?? null,
            }));
        } catch (error) {
            console.error('Error registering service worker:', error);
        }
    }

    useEffect(() => {
        updateStatus();
    }, [store]);

    async function postSubscription(subscription?: PushSubscription) {
        console.log('postSubscription', { subscription });

        if (!subscription) {
            showAlert('postSubscription - subscription cannot be empty');
            return;
        }

        const response = await fetch('/web-push/subscription', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId, subscription }),
        });

        console.log('postSubscription', { response });
    }

    async function deleteSubscription() {
        const response = await fetch('/web-push/subscription', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ userId }),
        });

        console.log('deleteSubscription', { response });
    }

    async function subscribe() {
        if (store.pushSubscription) {
            showAlert('subscribe - already subscribed');
            return;
        }

        try {
            const response = await fetch('/web-push/vapid-public-key',
                {
                    method: 'GET',
                }
            ); // 절대 경로 사용
            const vapidPublicKey = await response.text();
            // console.log('subscribe', vapidPublicKey);

            const registration = store.serviceWorkerRegistration;

            if (!registration) {
                showAlert('subscribe - service worker is not registered');
                return;
            }

            const pushManager = registration.pushManager;

            if (!pushManager) {
                showAlert('subscribe - push manager is not available');
                return;
            }

            const subscription = await pushManager.subscribe({
                applicationServerKey: vapidPublicKey,
                userVisibleOnly: true,
            });
            setStore((prevStore) => ({
                ...prevStore,
                pushSubscription: subscription,
            }));
            await postSubscription(subscription);
        } catch (error) {
            console.error('subscribe', { error });
        } finally {
            updateStatus();
        }
    }


    async function unsubscribe() {
        const subscription = store.pushSubscription;

        if (!subscription) {
            showAlert('unsubscribe - push subscription does not exist');
            return;
        }

        try {
            const unsubscribed = await subscription.unsubscribe();
            setStore((prevStore) => ({
                ...prevStore,
                pushSubscription: null,
            }));
            console.log('unsubscribe', { unsubscribed });
            await deleteSubscription();
        } catch (error) {
            console.error('unsubscribe', { error });
        } finally {
            updateStatus();
        }
    }

    async function sendPushNotification() {
        const targetId = elements.current?.targetUserId?.value;
        const message = elements.current?.message?.value ?? '';
        console.log('sendPushNotification', { targetId, message });

        if (!targetId) {
            showAlert('Target userId cannot be empty');
            return;
        }

        const response = await fetch('/web-push/send-push-notification', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ targetId, message }),
        });

        console.log('sendPushNotification', { response });
        setText(elements.current?.sendStatus, `(${response.status}) ${response.statusText} / ${new Date()}`);
    }

    function setText(element: HTMLElement | null, value: string | boolean) {
        if (!element) return;
        element.textContent = value.toString();
        element.classList.remove('t');
        element.classList.remove('f');
        typeof value === 'boolean' && element.classList.add(value ? 't' : 'f');
    }

    async function updateStatus() {
        setText(elements.current?.currentUserId, userId as string);
        setText(elements.current?.registration, !!store.serviceWorkerRegistration);
        setText(elements.current?.pushSupport, store.pushSupport);
        setText(elements.current?.notificationPermission, Notification.permission);
        setText(elements.current?.subscription, JSON.stringify(store.pushSubscription, null, 2));
    }

    function showAlert(message: string) {
        console.warn(message);
        alert(message);
    }

    function logout() {
        localStorage.removeItem('userId');
        location.href = '/notification/login';
    }

    useEffect(() => {
        elements.current.currentUserId = document.getElementById('current_user_id') as HTMLParagraphElement;
        elements.current.registration = document.getElementById('registration_status') as HTMLParagraphElement;
        elements.current.pushSupport = document.getElementById('push_support_status') as HTMLParagraphElement;
        elements.current.notificationPermission = document.getElementById('notification_permission_status') as HTMLParagraphElement;
        elements.current.subscription = document.getElementById('subscription') as HTMLPreElement;
        elements.current.sendStatus = document.getElementById('send_status') as HTMLParagraphElement;
        elements.current.message = document.getElementById('message') as HTMLInputElement;
        elements.current.targetUserId = document.getElementById('target_user_id') as HTMLInputElement;
        updateStatus();
    }, []);

    useEffect(() => {
        registerServiceWorker();
    }, []);

    return (
        <html>
        <head>
            <meta charSet="utf-8" />
            <meta name="viewport" content="width=device-width,initial-scale=1" />
            <title>Web Push</title>
            <link
                rel="stylesheet"
                as="style"
                crossOrigin="anonymous"
                href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard/dist/web/static/pretendard.css"
            />
            <link rel="stylesheet" href="/css/common.css" />
        </head>
        <body>
        <header>
            <h1>Web Push</h1>
        </header>
        <main>
            <section>
                <h2>👀 Status</h2>
                <div className="item">
                    Current User ID: <u><p className="inline" id="current_user_id">{userId}</p></u>
                </div>
                <div className="item">
                    ServiceWorker registered: <p className="inline" id="registration_status"></p>
                    <br />
                    Push Support: <p className="inline" id="push_support_status"></p>
                    <br />
                    Notification permission: <p className="inline" id="notification_permission_status"></p>
                </div>
                <div className="item">
                    <pre id="subscription"></pre>
                </div>
            </section>
            <section>
                <h2>👉 Subscribe</h2>
                <div className="item">
                    <button onClick={subscribe}>Subscribe</button>
                    <button onClick={unsubscribe}>Unsubscribe</button>
                </div>
            </section>
            <section>
                <h2>🚀 Send Push Notification</h2>
                <div className="item">
                    <input type="text" id="message" defaultValue="Hello, World!" />
                </div>
                <div className="item group">
                    <input className="fill" type="text" id="target_user_id" placeholder="Target User ID" />
                    <button onClick={sendPushNotification}>Send</button>
                </div>
                <div className="item">
                    <p className="inline secondary" id="send_status">-</p>
                </div>
            </section>
            <section>
                <h2>🔥 Logout</h2>
                <div className="item">
                    <button onClick={logout}>Logout</button>
                </div>
            </section>
        </main>
        <script src="/index.js"></script>
        </body>
        </html>
    );
};

export default IndexPage;
