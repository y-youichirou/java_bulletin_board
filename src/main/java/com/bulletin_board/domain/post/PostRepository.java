package com.bulletin_board.domain.post;

import com.bulletin_board.domain.post.value_object.PostId;


public interface PostRepository {
    void save(Post post);
    void update(Post post);
    void delete(Post post);
    void findById(PostId postId);
    void findAll();
}
