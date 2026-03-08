package com.bulletin_board.domain.post.valueObject

import spock.lang.Specification
import spock.lang.Unroll

class PostStatusSpec extends Specification {
    @Unroll
    def "PostStatusの基本検証: #description"() {
        expect:
        assertion()

        where:
        description                        | assertion
        "PUBLICが存在する"                  | { -> PostStatus.PUBLIC != null }
        "PRIVATEが存在する"                 | { -> PostStatus.PRIVATE != null }
    }

    @Unroll
    def "文字列 '#input' からPostStatusを取得: #expected"() {
        expect:
        PostStatus.valueOf(input) == expected

        where:
        input      || expected
        "PUBLIC"   || PostStatus.PUBLIC
        "PRIVATE"  || PostStatus.PRIVATE
    }

    def "不正な文字列からPostStatusを取得するとIllegalArgumentExceptionがスローされる"() {
        when:
        PostStatus.valueOf("TEST")

        then:
        thrown(IllegalArgumentException)
    }
}

