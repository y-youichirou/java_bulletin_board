package com.bulletin_board.domain.post

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
 * Postの結合テスト
 *
 * @see PostService Application層のユースケース
 * @see PostRepository Domain層のリポジトリインターフェース
 * @see InMemoryPostRepository Infrastructure層のリポジトリ実装
 * @see Post Domain層のエンティティ
 */
class PostIntegrationSpec extends Specification {

    PostRepository postRepository
    PostService postService

    def setup() {
        // 結合テストでは実際の実装を使用（モックは使わない）
        // これにより、レイヤー間の実際のデータフローを確認できる
        postRepository = new InMemoryPostRepository()
        postService = new PostService(postRepository)
    }

    def cleanup() {
        // 各テスト後にリポジトリをクリーンアップ
        // テストの独立性を保つため、他のテストに影響を与えないようにする
        def allPosts = postRepository.findAll().collect { it }
        allPosts.each { post ->
            postRepository.delete(post.getPostId())
        }
    }

    /**
     * 投稿の作成から取得までの基本的なライフサイクルをテスト
     *
     * 確認内容：
     * - Service層を経由したドメインオブジェクトの永続化
     * - Repository層でのデータ保存
     * - 保存したデータの正確な取得
     */
    def "投稿の作成から取得までのライフサイクル結合テスト"() {
        given: "新規投稿のドメインオブジェクト"
        def createTime = LocalDateTime.of(2026, 1, 18, 10, 0, 0)
        def postId = new PostId(1)
        def post = new Post(
            postId,
            new Title("新規投稿のタイトル"),
            PostStatus.PRIVATE,
            new Content("新規投稿のコンテンツ"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )

        when: "サービス経由で投稿を作成"
        postService.createPost(post)

        and: "作成した投稿を取得"
        def retrievedPost = postService.findPostById(postId)

        then: "取得した投稿が正しいことを確認"
        retrievedPost != null
        retrievedPost.getPostId().id() == 1
        retrievedPost.getTitle().title() == "新規投稿のタイトル"
        retrievedPost.getStatus() == PostStatus.PRIVATE
        retrievedPost.getContent().content() == "新規投稿のコンテンツ"
        retrievedPost.getCreateDateTime().timestamp() == createTime
        retrievedPost.getUpdateDateTime().timestamp() == null
    }

    def "投稿の更新結合テスト - サービス層とリポジトリ層の連携"() {
        given: "既存の投稿を作成"
        def createTime = LocalDateTime.of(2026, 1, 18, 10, 0, 0)
        def postId = new PostId(1)
        def originalPost = new Post(
            postId,
            new Title("初回投稿"),
            PostStatus.PRIVATE,
            new Content("下書き内容"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )
        postService.createPost(originalPost)

        when: "投稿を更新"
        def updateTime = LocalDateTime.of(2026, 1, 19, 15, 30, 0)
        def updatedPost = new Post(
            postId,
            new Title("更新後のタイトル"),
            PostStatus.PUBLIC,
            new Content("公開する内容"),
            new CreateDateTime(createTime),
            new UpdateDateTime(updateTime)
        )
        postService.updatePost(updatedPost)

        and: "更新後の投稿を取得"
        def retrievedPost = postService.findPostById(postId)

        then: "更新が正しく反映されていることを確認"
        retrievedPost.getPostId().id() == 1
        retrievedPost.getTitle().title() == "更新後のタイトル"
        retrievedPost.getStatus() == PostStatus.PUBLIC
        retrievedPost.getContent().content() == "公開する内容"
        retrievedPost.getUpdateDateTime().timestamp() == updateTime
    }

    def "複数投稿の作成と全件取得の結合テスト"() {
        given: "複数の投稿を作成"
        def createTime = LocalDateTime.now()
        def posts = [
            new Post(
                new PostId(1),
                new Title("投稿1"),
                PostStatus.PUBLIC,
                new Content("公開投稿1"),
                new CreateDateTime(createTime),
                new UpdateDateTime(null)
            ),
            new Post(
                new PostId(2),
                new Title("投稿2"),
                PostStatus.PRIVATE,
                new Content("非公開投稿2"),
                new CreateDateTime(createTime),
                new UpdateDateTime(null)
            ),
            new Post(
                new PostId(3),
                new Title("投稿3"),
                PostStatus.PUBLIC,
                new Content("公開投稿3"),
                new CreateDateTime(createTime),
                new UpdateDateTime(null)
            )
        ]

        when: "サービス経由で全ての投稿を保存"
        posts.each { post ->
            postService.createPost(post)
        }

        and: "全ての投稿を取得"
        def allPosts = postService.findAllPosts()

        then: "全ての投稿が正しく取得できることを確認"
        allPosts.size() == 3
        allPosts.collect { it.getPostId().id() }.sort() == [1, 2, 3]
        allPosts.findAll { it.getStatus() == PostStatus.PUBLIC }.size() == 2
        allPosts.findAll { it.getStatus() == PostStatus.PRIVATE }.size() == 1
    }

    def "投稿の削除結合テスト"() {
        given: "投稿を作成"
        def postId = new PostId(1)
        def post = new Post(
            postId,
            new Title("削除予定の投稿タイトル"),
            PostStatus.PUBLIC,
            new Content("削除予定の投稿コメント"),
            new CreateDateTime(LocalDateTime.now()),
            new UpdateDateTime(null)
        )
        postService.createPost(post)

        and: "投稿が存在することを確認"
        def beforeDelete = postService.findPostById(postId)
        assert beforeDelete != null

        when: "投稿を削除"
        postService.deletePost(postId)

        and: "削除後に取得を試みる"
        def afterDelete = postService.findPostById(postId)

        then: "投稿が削除されていることを確認"
        afterDelete == null
    }

}

