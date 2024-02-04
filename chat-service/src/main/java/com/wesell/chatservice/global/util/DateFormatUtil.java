package com.wesell.chatservice.global.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Component
public class DateFormatUtil {

    // 대화창 날짜 표시 형태
    public String formatSendDate(LocalDateTime sendDate){
        return sendDate.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
