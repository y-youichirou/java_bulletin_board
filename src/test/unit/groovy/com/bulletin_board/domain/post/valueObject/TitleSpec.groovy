package com.bulletin_board.domain.post.valueObject

import spock.lang.Specification
import spock.lang.Unroll

class TitleSpec extends Specification {
    @Unroll
    def "Titleを作成: #description"() {
        when:
        def title = new Title(input)

        then:
        title.title() == expected

        where:
        description     | input          || expected
        "正常なタイトル"   | "投稿のタイトル"  || "投稿のタイトル"
        "空のタイトル"    | ""             || ""
        "nullのタイトル" | null           || null
    }

    @Unroll
    def "Titleの等価性: #description"() {
        given:
        def title1 = new Title(input1)
        def title2 = new Title(input2)

        expect:
        (title1 == title2) == expectedEqual
        (title1.hashCode() == title2.hashCode()) == expectedHashCodeEqual

        where:
        description              | input1       | input2       || expectedEqual | expectedHashCodeEqual
        "同じ値を持つ場合は等しい"   | "同じタイトル" | "同じタイトル" || true          | true
        "異なる値を持つ場合は等しくない" | "タイトル1"   | "タイトル2"   || false         | false
    }
}

