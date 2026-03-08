package com.bulletin_board.domain.post.valueObject

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class CreateDateTimeSpec extends Specification {
    @Unroll
    def "CreateDateTimeを作成: #description"() {
        when:
        def createDateTime = new CreateDateTime(input)

        then:
        createDateTime.timestamp() == expected

        where:
        description           | input                                   || expected
        "正常なLocalDateTime" | LocalDateTime.of(2026, 1, 18, 10, 30, 45) || LocalDateTime.of(2026, 1, 18, 10, 30, 45)
        "null"               | null                                    || null
    }

    @Unroll
    def "CreateDateTimeの等価性: #description"() {
        given:
        def createDateTime1 = new CreateDateTime(input1)
        def createDateTime2 = new CreateDateTime(input2)

        expect:
        (createDateTime1 == createDateTime2) == expectedEqual
        (createDateTime1.hashCode() == createDateTime2.hashCode()) == expectedHashCodeEqual

        where:
        description              | input1                                   | input2                                   || expectedEqual | expectedHashCodeEqual
        "同じ値を持つ場合は等しいこと"   | LocalDateTime.of(2026, 1, 18, 10, 30, 45) | LocalDateTime.of(2026, 1, 18, 10, 30, 45) || true          | true
        "異なる値を持つ場合は等しくないこと" | LocalDateTime.of(2026, 1, 18, 10, 30, 45) | LocalDateTime.of(2026, 1, 18, 10, 30, 46) || false         | false
    }

    def "CreateDateTimeが作成できること"() {
        when:
        def now = LocalDateTime.now()
        def createDateTime = new CreateDateTime(now)

        then:
        createDateTime.timestamp() == now
    }
}

