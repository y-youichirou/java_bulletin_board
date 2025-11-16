package com.bulletin_board.domain.post;

import com.bulletin_board.domain.post.value_object.*;

import java.time.LocalDateTime;

public class Post {
    @Id
    private PostId id;

    private Title title;
    private PostStatus status;
    private Content content;
    private CreateDateTime createDateTime;
    private UpdateDateTime updateDateTime;

    public Post(PostId id, Title title, PostStatus status, Content content) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.content = content;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

}
