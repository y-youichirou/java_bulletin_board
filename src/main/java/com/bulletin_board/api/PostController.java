package com.bulletin_board.api;

import com.bulletin_board.domain.post.Post;
import com.bulletin_board.domain.post.valueObject.PostId;
import com.bulletin_board.service.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> getAllPosts() {
        return postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public Post getPost(@RequestBody PostId postId) {
        return postService.findPostById(postId);
    }

    @PostMapping
    public void createPost(@RequestBody Post post) {
        postService.createPost(post);
    }

    @PutMapping("/{id}")
    public void updatePost(@RequestBody Post post) {
        postService.updatePost(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable PostId postId) {
        Post post = postService.findPostById(postId);
        postService.deletePost(post.getPostId());
    }
}
