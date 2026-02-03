package com.bulletin_board.unit.valueObject;

import com.bulletin_board.domain.post.valueObject.Title;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTitle {
    @Test
    @DisplayName("Titleが正しく取得できることを確認")
    public void testTitle() {
        Title title = new Title("テストタイトル");
        assertEquals("テストタイトル", title.title());
    }

}
