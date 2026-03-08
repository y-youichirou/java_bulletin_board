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

class PostSpec extends Specification {
    @Unroll
    def "Postを作成: #description"() {
        when:
        def post = new Post(
            new PostId(postId),
            new Title(title),
            status,
            new Content(content),
            new CreateDateTime(createDateTime),
            new UpdateDateTime(updateDateTime)
        )

        then:
        post.getPostId().id() == postId
        post.getTitle().title() == title
        post.getStatus() == status
        post.getContent().content() == content
        post.getCreateDateTime().timestamp() == createDateTime
        post.getUpdateDateTime().timestamp() == updateDateTime

        where:
        description          | postId | title      | status              | content    | createDateTime                           | updateDateTime
        "完全なデータ"         | 1      | "タイトル"  | PostStatus.PUBLIC   | "本文内容" | LocalDateTime.of(2026, 1, 18, 10, 0, 0) | LocalDateTime.of(2026, 1, 19, 15, 30, 0)
        "PRIVATEステータス"   | 2      | "秘密"     | PostStatus.PRIVATE  | "秘密情報" | LocalDateTime.of(2026, 1, 18, 10, 0, 0) | null
        "空のタイトルと本文"   | 3      | ""         | PostStatus.PUBLIC   | ""         | LocalDateTime.of(2026, 1, 18, 10, 0, 0) | null
        "nullのタイトルと本文" | 0      | null       | PostStatus.PRIVATE  | null       | LocalDateTime.of(2026, 1, 18, 10, 0, 0) | null
    }

    def "Postの各プロパティを取得できることを確認"() {
        given:
        def postId = new PostId(123)
        def title = new Title("テストタイトル")
        def status = PostStatus.PUBLIC
        def content = new Content("テスト本文")
        def createDateTime = new CreateDateTime(LocalDateTime.of(2026, 1, 18, 10, 30, 45))
        def updateDateTime = new UpdateDateTime(LocalDateTime.of(2026, 1, 19, 15, 20, 30))

        when:
        def post = new Post(postId, title, status, content, createDateTime, updateDateTime)

        then:
        post.getPostId() == postId
        post.getTitle() == title
        post.getStatus() == status
        post.getContent() == content
        post.getCreateDateTime() == createDateTime
        post.getUpdateDateTime() == updateDateTime
    }

    @Unroll
    def "異なるステータスでPostを作成: #status"() {
        when:
        def post = new Post(
            new PostId(1),
            new Title("タイトル"),
            status,
            new Content("本文"),
            new CreateDateTime(LocalDateTime.of(2026, 1, 18, 10, 0, 0)),
            new UpdateDateTime(null)
        )

        then:
        post.getPostId().id() == 1
        post.getTitle().title() == "タイトル"
        post.getStatus() == status
        post.getContent().content() == "本文"
        post.getCreateDateTime().timestamp() == LocalDateTime.of(2026, 1, 18, 10, 0, 0)
        post.getUpdateDateTime().timestamp() == null

        where:
        status << [PostStatus.PUBLIC, PostStatus.PRIVATE]
    }

    def "Postの作成時刻と更新時刻を設定できる"() {
        given:
        def createTime = LocalDateTime.of(2026, 1, 18, 10, 30, 45)
        def updateTime = LocalDateTime.of(2026, 1, 19, 15, 20, 30)

        when:
        def post = new Post(
            new PostId(1),
            new Title("タイトル"),
            PostStatus.PUBLIC,
            new Content("本文"),
            new CreateDateTime(createTime),
            new UpdateDateTime(updateTime)
        )

        then:
        post.getPostId().id() == 1
        post.getTitle().title() == "タイトル"
        post.getStatus() == PostStatus.PUBLIC
        post.getContent().content() == "本文"
        post.getCreateDateTime().timestamp() == createTime
        post.getUpdateDateTime().timestamp() == updateTime
    }

    def "更新時刻がnullのPostを作成できる"() {
        given:
        def createTime = LocalDateTime.now()

        when:
        def post = new Post(
            new PostId(1),
            new Title("新規投稿"),
            PostStatus.PUBLIC,
            new Content("新しい本文"),
            new CreateDateTime(createTime),
            new UpdateDateTime(null)
        )

        then:
        post.getPostId().id() == 1
        post.getTitle().title() == "新規投稿"
        post.getStatus() == PostStatus.PUBLIC
        post.getContent().content() == "新しい本文"
        post.getCreateDateTime().timestamp() == createTime
        post.getUpdateDateTime().timestamp() == null
    }
}

