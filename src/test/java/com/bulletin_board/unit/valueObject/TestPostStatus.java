package com.bulletin_board.unit.valueObject;

import com.bulletin_board.domain.post.valueObject.PostStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPostStatus {
    @Test
    @DisplayName("PostStatusの値が正しく取得できることを確認")
    public void testPostStatus(){
        assertEquals("PUBLIC", PostStatus.PUBLIC.name());
        assertEquals("PRIVATE", PostStatus.PRIVATE.name());
    }

    @Test
    @DisplayName("PostStatusの値が異なることを確認")
    public void testPostStatusError(){
        assertNotEquals("PUBLIC", PostStatus.PRIVATE.name());
        assertNotEquals("PRIVATE", PostStatus.PUBLIC.name());
    }

}
