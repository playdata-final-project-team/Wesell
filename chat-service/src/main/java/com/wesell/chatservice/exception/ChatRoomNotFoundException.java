package com.wesell.chatservice.exception;

public class ChatRoomNotFoundException extends RuntimeException{
    public ChatRoomNotFoundException() {
        super("해당 채팅방이 없습니다.");
    }
}
