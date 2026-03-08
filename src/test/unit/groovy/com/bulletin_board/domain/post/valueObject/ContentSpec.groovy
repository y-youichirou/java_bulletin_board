package com.bulletin_board.domain.post.valueObject

import spock.lang.Specification

class ContentSpec extends Specification {
    def "本文を作成"() {
        when:
        def content = new Content(inputContent)

        then:
        content.content() == expectedContent

        where:
        inputContent || expectedContent
        "本文"        || "本文"
        ""           || ""
    }

}

