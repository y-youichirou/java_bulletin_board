package com.bulletin_board.unit.valueObject;

import com.bulletin_board.domain.post.valueObject.PostId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPostId {
    @Test
    @DisplayName("PostIdのidが正しく取得できることを確認")
    public void testPostId(){
        PostId postId1 = new PostId(1);
        PostId postId2 = new PostId(10);
        assertEquals(1, postId1.id());
        assertEquals(10, postId2.id());
    }

    @Test
    @DisplayName("PostIdのidが負数の場合を確認")
    public void testPostIdErrorNegativeNumber() {
        try {
            PostId postId = new PostId(-1);
        } catch (IllegalArgumentException e) {
            assertEquals("PostIdの負数チェックでエラーが発生しました", e.getMessage());
        }
    }

}
