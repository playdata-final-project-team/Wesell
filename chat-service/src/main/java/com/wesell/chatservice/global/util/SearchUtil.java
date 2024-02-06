package com.wesell.chatservice.global.util;

import com.wesell.chatservice.domain.entity.ChatMessage;
import com.wesell.chatservice.domain.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class SearchUtil {

    /**
     * 채팅방 내 최신 전송된 날짜를 조회하기 위한 기능
     * 
     * @param chatRoom
     * @return formatted 날짜
     */
    public String searchLastSendDate(ChatRoom chatRoom) {
        List <ChatMessage> messages = chatRoom.getChatMessageList();

        if(messages.isEmpty()) {
            return "";
        }
        else{
            return messages.stream()
                    .max(Comparator.comparing(ChatMessage::getSendDate))
                    .map(ChatMessage::getSendDate).get().format(DateTimeFormatter.ofPattern("MM월 dd일 E요일"));
        }
    }

    public String searchLastSendMessage(ChatRoom chatRoom){
        List<ChatMessage> messages = chatRoom.getChatMessageList();
        if(messages.isEmpty()){
            return "";
        }else{
            String lastMessage = messages.stream()
                    .max(Comparator.comparing(ChatMessage::getSendDate))
                    .map(ChatMessage::getContent)
                    .get();
            return lastMessage;

        }
    }

}
