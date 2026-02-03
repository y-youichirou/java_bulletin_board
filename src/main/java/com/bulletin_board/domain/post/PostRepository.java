package com.bulletin_board.domain.post;

import com.bulletin_board.domain.post.valueObject.PostId;

import java.util.Collection;


public interface PostRepository {
    void save(Post post);
    void update(Post post);
    void delete(PostId postId);
    Post findById(PostId postId);
    Collection<Post> findAll();
}
