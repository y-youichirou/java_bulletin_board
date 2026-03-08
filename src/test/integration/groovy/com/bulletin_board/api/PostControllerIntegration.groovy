package com.bulletin_board.api

import com.bulletin_board.domain.post.Post
import com.bulletin_board.domain.post.PostRepository
import com.bulletin_board.domain.post.valueObject.Content
import com.bulletin_board.domain.post.valueObject.CreateDateTime
import com.bulletin_board.domain.post.valueObject.PostId
import com.bulletin_board.domain.post.valueObject.PostStatus
import com.bulletin_board.domain.post.valueObject.Title
import com.bulletin_board.domain.post.valueObject.UpdateDateTime
import com.bulletin_board.infrastructure.InMemoryPostRepository
import com.bulletin_board.service.PostService
import spock.lang.Specification

import java.time.LocalDateTime

/**
 * PostControllerの統合テスト（レイヤー統合確認）
 * 
 * @see PostController Presentation層のコントローラー
 * @see PostService Application層のユースケース
 * @see PostRepository Domain層のリポジトリインターフェース
 * @see InMemoryPostRepository Infrastructure層のリポジトリ実装
 */
class PostControllerIntegration extends Specification {

    private PostController controller
    private PostService postService
    private PostRepository postRepository

    def setup() {
        // 統合テストでは実際のコンポーネントを使用
        postRepository = new InMemoryPostRepository()
        postService = new PostService(postRepository)
        controller = new PostController(postService)
    }

    def cleanup() {
        // クリーンアップ
        // ConcurrentModificationExceptionを避けるため、コレクションのコピーを作成
        def allPosts = postService.findAllPosts()
        def postIds = allPosts.collect { post -> post.getPostId() }
        postIds.each { postId ->
            postService.deletePost(postId)
        }
    }

    /**
     * APIのテスト
     */
    def "getPost() - 1件のPUBLIC投稿を作成"() {
        given: "1件の投稿を作成"
        def createTime = LocalDateTime.of(2026, 2, 15, 10, 0, 0)
        def post = new Post(
                new PostId(1),
                new Title("新規投稿タイトル"),
                PostStatus.PUBLIC,
                new Content("投稿内容"),
                new CreateDateTime(createTime),
                new UpdateDateTime(null)
        )
        postService.createPost(post)

        when: "投稿を取得"
        def retrieved = controller.getPost(new PostId(1))

        then: "投稿が取得できていること"
        retrieved != null
        retrieved.getPostId().id() == 1
        retrieved.getTitle().title() == "新規投稿タイトル"
        retrieved.getStatus() == PostStatus.PUBLIC
        retrieved.getContent().content() == "投稿内容"
    }

    def "createPost() - 1件のPRIVATEの投稿作成"() {
        given: "新規投稿のドメインオブジェクト"
        def createTime = LocalDateTime.of(2026, 2, 15, 10, 0, 0)
        def post = new Post(
                new PostId(1),
                new Title("新規投稿タイトル"),
                PostStatus.PRIVATE,
                new Content("投稿内容"),
                new CreateDateTime(createTime),
                new UpdateDateTime(null)
        )

        when: "Controller経由で投稿を作成"
        controller.createPost(post)

        and: "作成された投稿を取得"
        def created = postService.findPostById(new PostId(1))

        then: "投稿が保存されていること"
        created != null
        created.getTitle().title() == "新規投稿タイトル"
        created.getStatus() == PostStatus.PRIVATE
        created.getContent().content() == "投稿内容"
    }

    def "getAllPosts() 複数の投稿を取得"() {
        given: "3件の投稿を作成"
        def createTime = LocalDateTime.of(2026, 2, 15, 10, 0, 0)

        PostId postId1 = new PostId(1)
        PostId postId2 = new PostId(2)
        PostId postId3 = new PostId(3)

        def post1 = new Post(
            postId1,
            new Title("タイトル1"),
            PostStatus.PUBLIC,
            new Content("内容1"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )
        postService.createPost(post1)

        def post2 = new Post(
            postId2,
            new Title("タイトル2"),
            PostStatus.PUBLIC,
            new Content("内容2"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )
        postService.createPost(post2)

        def post3 = new Post(
            postId3,
            new Title("タイトル3"),
            PostStatus.PUBLIC,
            new Content("内容3"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )
        postService.createPost(post3)

        when: "全ての投稿を取得"
        def posts = controller.getAllPosts()

        then: "全ての投稿の数とidが正しく取得できること"
        posts.size() == 3
        posts.collect {post -> post.getPostId().id() }.sort() == [1, 2, 3]
    }

    def "updatePost() - 投稿更新"() {
        given: "既存の投稿が存在する"
        def createTime = LocalDateTime.of(2026, 2, 15, 10, 0, 0)
        def createPost = new Post(
            new PostId(1),
            new Title("更新前タイトル"),
            PostStatus.PRIVATE,
            new Content("更新前内容"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )
        postService.createPost(createPost)

        when: "Controller経由で投稿を更新"
        def updateTime = LocalDateTime.of(2026, 2, 15, 15, 0, 0)
        def updatedPost = new Post(
            new PostId(1),
            new Title("更新後タイトル"),
            PostStatus.PUBLIC,
            new Content("更新後内容"),
            new CreateDateTime(createTime),
            new UpdateDateTime(updateTime)
        )
        controller.updatePost(updatedPost)

        and: "更新された投稿を取得"
        def result = postService.findPostById(new PostId(1))

        then: "投稿が更新されていること"
        result.getTitle().title() == "更新後タイトル"
        result.getStatus() == PostStatus.PUBLIC
        result.getContent().content() == "更新後内容"
        result.getUpdateDateTime().timestamp() == updateTime
    }

    def "deletePost() - 投稿削除"() {
        given: "削除対象の投稿が存在する"
        def post = new Post(
            new PostId(1),
            new Title("削除予定"),
            PostStatus.PUBLIC,
            new Content("削除される内容"),
            new CreateDateTime(LocalDateTime.now()),
            new UpdateDateTime(null)
        )
        postService.createPost(post)

        and: "投稿が存在すること"
        assert postService.findPostById(new PostId(1)) != null

        when: "投稿を削除"
        controller.deletePost(new PostId(1))

        and: "削除後に取得"
        def deleted = postService.findPostById(new PostId(1))

        then: "投稿が削除されていること"
        deleted == null
    }

}

