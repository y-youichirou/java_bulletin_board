package com.bulletin_board.unit.valueObject;

import com.bulletin_board.domain.post.valueObject.UpdateDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class TestUpdateDateTime {
    @Test
    @DisplayName("日付フォーマット確認")
    public void testCreateDateTime() {
        LocalDateTime nowTime = LocalDateTime.of(2024, 6, 1, 12, 34, 56);
        UpdateDateTime createDateTime = new UpdateDateTime(nowTime);

        String formatted = createDateTime.timestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        assertEquals("2024-06-01 12:34:56", formatted);
    }

    @Test
    @DisplayName("日付フォーマットがエラーになるパターンを確認")
    public void testCreateDateTimeError() {
        try {
            LocalDateTime errorFormat = LocalDateTime.of(2024, 13, 1, 12, 34, 56); // 月が13月で不正
            UpdateDateTime createDateTime = new UpdateDateTime(errorFormat);
        } catch (Exception e) {
            assertInstanceOf(DateTimeException.class, e);
        }
    }
}
