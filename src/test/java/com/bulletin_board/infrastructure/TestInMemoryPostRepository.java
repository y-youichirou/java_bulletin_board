package com.bulletin_board.infrastructure;

import com.bulletin_board.domain.post.Post;
import com.bulletin_board.domain.post.valueObject.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.bulletin_board.service.PostService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

public class TestInMemoryPostRepository {
    private PostId postId = new PostId(123);
    private Title title = new Title("タイトル");
    private Content content = new Content("コンテンツ");

    private Post post = new Post(
            postId,
            title,
            PostStatus.PUBLIC,
            content,
            new CreateDateTime(LocalDateTime.of(2025, 12, 1, 0, 0, 0)),
            new UpdateDateTime(LocalDateTime.of(2025, 12, 1, 0, 0, 0))
    );

    @Test
    @DisplayName("saveメソッドのテスト")
    public void testSaveAndFindById() {
        InMemoryPostRepository repositorySave = new InMemoryPostRepository();
        repositorySave.save(this.post);

        // 検証
        Post savedPost = repositorySave.findById(postId);
        assertSame(this.post, savedPost, "保存したPostと取得したPost一致しない");
        }

    @Test
    @DisplayName("updateメソッドの正常系テスト")
    public void testSaveAndUpdate() {
        Post postUpdate = new Post(
                this.postId,
                this.title,
                PostStatus.PUBLIC,
                new Content("変更後コンテンツ"),
                new CreateDateTime(LocalDateTime.of(2025, 12, 1, 0, 0, 0)),
                new UpdateDateTime(LocalDateTime.of(2025, 12, 1, 0, 0, 0))
        );

        InMemoryPostRepository repositoryUpdate = new InMemoryPostRepository();
        repositoryUpdate.update(postUpdate);

        // 検証
        Post updatedPostFindById = repositoryUpdate.findById(this.postId);
        assertSame(postUpdate, updatedPostFindById, "更新したPostと取得したPost一致しない");

        }

    @Test
    @DisplayName("deleteメソッド正常系テスト")
    public void testSaveAndDelete() {
        InMemoryPostRepository repositoryDelete = new InMemoryPostRepository();
        repositoryDelete.save(this.post);
        repositoryDelete.delete(this.postId);

        // 検証
        Post deletedPostFindById = repositoryDelete.findById(this.postId);
        assertSame(null, deletedPostFindById, "削除したPostがまだ存在している");
    }

    @Test
    @DisplayName("findAllメソッドの正常系テスト")
    public void testSaveAndFindAll(){
        InMemoryPostRepository repositoryFindAll = new InMemoryPostRepository();
            // 2つ目のPostを作成して保存
            Post anotherPost = new Post(
                    new PostId(456),
                    new Title("タイトル2"),
                    PostStatus.PRIVATE,
                    new Content("コンテンツ2"),
                    new CreateDateTime(LocalDateTime.of(2025, 12, 2, 0, 0, 0)),
                    new UpdateDateTime(LocalDateTime.of(2025, 12, 2, 0, 0, 0))
            );
        repositoryFindAll.save(this.post);
        repositoryFindAll.save(anotherPost);

        // 検証
        int size = repositoryFindAll.findAll().size();
        assertSame(2, size, "findAllの件数が一致しない");
    }
}
