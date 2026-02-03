package com.bulletin_board.unit.valueObject;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.bulletin_board.domain.post.valueObject.CreateDateTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;

import static org.junit.jupiter.api.Assertions.*;

public class TestCreateDateTime {
    @Test
    @DisplayName("付フォーマット確認")
    public void testCreateDateTime() {
        LocalDateTime nowTime = LocalDateTime.of(2024, 6, 1, 12, 34, 56);
        CreateDateTime createDateTime = new CreateDateTime(nowTime);

        String formatted = createDateTime.timestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals("2024-06-01 12:34:56", formatted);
    }

    @Test
    @DisplayName("日付フォーマットがエラーになるパターンを確認")
    public void testCreateDateTimeError() {
        try {
            LocalDateTime errorFormat = LocalDateTime.of(2024, 13, 1, 12, 34, 56); // 月が13月で不正
            CreateDateTime createDateTime = new CreateDateTime(errorFormat);
        } catch (Exception e) {
            assertInstanceOf(DateTimeException.class, e);
        }
    }

}
