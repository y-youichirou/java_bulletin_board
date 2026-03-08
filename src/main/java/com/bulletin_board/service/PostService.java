package com.bulletin_board.service;

import com.bulletin_board.domain.post.Post;
import com.bulletin_board.domain.post.PostRepository;
import com.bulletin_board.domain.post.valueObject.PostId;

import java.util.Collection;

public class PostService{
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void createPost(Post post) {
        this.postRepository.save(post);
    }

    public void updatePost(Post post) {
        this.postRepository.update(post);
    }

    public void deletePost(PostId postId) {
        this.postRepository.delete(postId);
    }

    public Post findPostById(PostId postId) {
        return this.postRepository.findById(postId);
    }

    public Collection<Post> findAllPosts() {
        return this.postRepository.findAll();
    }

}