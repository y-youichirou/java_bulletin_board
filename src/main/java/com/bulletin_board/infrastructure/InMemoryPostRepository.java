package com.bulletin_board.infrastructure;


import com.bulletin_board.domain.post.Post;
import com.bulletin_board.domain.post.PostRepository;
import com.bulletin_board.domain.post.valueObject.PostId;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryPostRepository implements PostRepository {

    private final Map<PostId, Post> posts = new HashMap<>();

    @Override
    public void save(Post post) {
        posts.put(post.getPostId(), post);
    }
    @Override
    public void update(Post post) {
        posts.put(post.getPostId(), post);
    }
    @Override
    public void delete(PostId postId) {
        posts.remove(postId);
    }
    @Override
    public Post findById(PostId postId) {
        return posts.get(postId);
    }
    @Override
    public Collection<Post> findAll() {
        return posts.values();
    }
}