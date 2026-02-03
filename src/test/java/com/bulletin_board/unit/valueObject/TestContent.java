package com.bulletin_board.unit.valueObject;

import com.bulletin_board.domain.post.valueObject.Content;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestContent {

    @Test
    @DisplayName("Contentのcontentが正しく取得できることを確認")
    public void testContent() {
        Content content1  = new Content("テストコンテンツ1");
        Content content2  = new Content("テストコンテンツ2");
        assertEquals("テストコンテンツ1", content1.content());
        assertEquals("テストコンテンツ2", content2.content());
    }
}
