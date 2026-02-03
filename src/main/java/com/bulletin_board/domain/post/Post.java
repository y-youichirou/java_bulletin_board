package com.bulletin_board.domain.post;

import com.bulletin_board.domain.post.valueObject.Content;
import com.bulletin_board.domain.post.valueObject.CreateDateTime;
import com.bulletin_board.domain.post.valueObject.PostId;
import com.bulletin_board.domain.post.valueObject.PostStatus;
import com.bulletin_board.domain.post.valueObject.Title;
import com.bulletin_board.domain.post.valueObject.UpdateDateTime;

public class Post {
    private PostId id;

    private Title title;
    private PostStatus status;
    private Content content;
    private CreateDateTime createDateTime;
    private UpdateDateTime updateDateTime;

    public Post(PostId id, Title title, PostStatus status, Content content, CreateDateTime createDateTime, UpdateDateTime updateDateTime) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.content = content;
        this.createDateTime = createDateTime;
        this.updateDateTime = updateDateTime;
    }

    public PostId getPostId() {
        return this.id;
    }

    public Title getTitle() {
        return this.title;
    }

    public PostStatus getStatus() {
        return this.status;
    }

    public Content getContent() {
        return this.content;
    }

    public CreateDateTime getCreateDateTime() {
        return this.createDateTime;
    }

    public UpdateDateTime getUpdateDateTime() {
        return this.updateDateTime;
    }
}
