package com.bulletin_board.api;

import com.bulletin_board.domain.post.Post;
import com.bulletin_board.domain.post.valueObject.PostId;
import com.bulletin_board.infrastructure.InMemoryPostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/posts")
public class PostController {
    private final InMemoryPostRepository postRepository = new InMemoryPostRepository();

    @GetMapping
    public Collection<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPost(@RequestBody int id) {
        return postRepository.findById(new PostId(id));
    }

    @PostMapping
    public void createPost(@RequestBody Post post) {
        postRepository.save(post);
    }

    @PutMapping("/{id}")
    public void updatePost(@PathVariable String id, @RequestBody Post post) {
        postRepository.update(post);
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable int id) {
        Post post = postRepository.findById(new PostId(id));
        postRepository.delete(post.getPostId());
    }
}
