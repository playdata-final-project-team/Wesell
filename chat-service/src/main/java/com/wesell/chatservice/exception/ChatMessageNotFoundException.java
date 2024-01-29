package com.wesell.chatservice.exception;

public class ChatMessageNotFoundException extends RuntimeException{

    public ChatMessageNotFoundException() {
        super("메시지를 찾을 수 없습니다.");
    }
}
