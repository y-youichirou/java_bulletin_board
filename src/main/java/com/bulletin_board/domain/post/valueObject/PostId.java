package com.bulletin_board.domain.post.valueObject;

public record PostId(int id) {
    public PostId {
        if (id < 0) {
            throw new IllegalArgumentException("0以上の値を指定してください");
        }
    }
}
