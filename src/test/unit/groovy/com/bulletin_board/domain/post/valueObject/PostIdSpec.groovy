package com.bulletin_board.domain.post.valueObject

import spock.lang.Specification
import spock.lang.Unroll

class PostIdSpec extends Specification {
    @Unroll
    def "PostIdを作成: #description"() {
        when:
        def postId = new PostId(input)

        then:
        postId.id() == expected

        where:
        description  | input || expected
        "1の正数"      | 1     || 1
        "3の正数"      | 3     || 3
    }

    @Unroll
    def "負の値でIllegalArgumentExceptionがスローされること: #description"() {
        when:
        new PostId(input)

        then:
        def ex = thrown(IllegalArgumentException)
        ex.message == "0以上の値を指定してください"

        where:
        description | input
        "-1"        | -1
        "-10"       | -10
        "-100"      | -100
    }
}

