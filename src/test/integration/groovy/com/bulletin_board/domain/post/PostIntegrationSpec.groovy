package com.bulletin_board.domain.post

import com.bulletin_board.domain.post.valueObject.Content
import com.bulletin_board.domain.post.valueObject.CreateDateTime
import com.bulletin_board.domain.post.valueObject.PostId
import com.bulletin_board.domain.post.valueObject.PostStatus
import com.bulletin_board.domain.post.valueObject.Title
import com.bulletin_board.domain.post.valueObject.UpdateDateTime
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

/**
 * 結合テスト（Integration Test）
 * 複数のコンポーネントが連携して動作することを確認するテスト
 */
class PostIntegrationSpec extends Specification {

    def "Post全体のライフサイクルをテスト"() {
        given: "新規投稿を作成"
        def createTime = LocalDateTime.of(2026, 1, 18, 10, 0, 0)
        def post = new Post(
            new PostId(1),
            new Title("初回投稿"),
            PostStatus.PRIVATE,
            new Content("下書き内容"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )

        expect: "初期状態の確認"
        post.getPostId().id() == 1

        when: "更新を想定（実際のアプリケーションでは更新メソッドを呼ぶ）"
        def updateTime = LocalDateTime.of(2026, 1, 19, 15, 30, 0)
        def updatedPost = new Post(
            post.getPostId(),
            new Title("更新後のタイトル"),
            PostStatus.PUBLIC,
            new Content("公開する内容"),
            new CreateDateTime(createTime),
            new UpdateDateTime(updateTime)
        )

        then: "更新後の状態確認"
        updatedPost.getPostId().id() == 1
        noExceptionThrown()
    }

    @Unroll
    def "複数の投稿を作成してステータス別に分類: #statusType"() {
        given: "複数の投稿"
        def posts = []

        when: "様々なステータスの投稿を作成"
        (1..5).each { i ->
            posts << new Post(
                new PostId(i),
                new Title("投稿${i}"),
                status,
                new Content("本文${i}"),
                new CreateDateTime(LocalDateTime.now()),
                new UpdateDateTime(LocalDateTime.now())
            )
        }

        then: "すべての投稿が作成される"
        posts.size() == 5
        posts.every { it.getPostId().id() > 0 }

        where:
        statusType | status
        "公開投稿" | PostStatus.PUBLIC
        "非公開投稿" | PostStatus.PRIVATE
    }

    def "投稿の作成日時と更新日時の整合性テスト"() {
        given: "作成日時"
        def createTime = LocalDateTime.of(2026, 1, 18, 10, 0, 0)

        when: "作成日時より前の更新日時で投稿を作成しようとする（通常はバリデーションで防ぐ）"
        def invalidUpdateTime = LocalDateTime.of(2026, 1, 17, 10, 0, 0)
        def post = new Post(
            new PostId(1),
            new Title("テスト"),
            PostStatus.PUBLIC,
            new Content("本文"),
            new CreateDateTime(createTime),
            new UpdateDateTime(invalidUpdateTime)
        )

        then: "現状は作成できる（将来的にバリデーションを追加する可能性あり）"
        noExceptionThrown()
    }

}

