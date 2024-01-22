package com.wesell.chatservice.global.util;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;

@Component
public class DateFormatUtil {

    // 대화창 날짜 표시 형태
    public String formatSendDate(LocalDateTime sendDate){
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd일 E요일", Locale.KOREA);
        Date date = Date.from(sendDate.atZone(ZoneId.systemDefault()).toInstant());
        return formatter.format(date);
    }
}
