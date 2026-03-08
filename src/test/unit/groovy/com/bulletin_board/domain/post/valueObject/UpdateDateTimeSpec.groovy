package com.bulletin_board.domain.post.valueObject

import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class UpdateDateTimeSpec extends Specification {
    @Unroll
    def "UpdateDateTimeを作成: #description"() {
        when:
        def updateDateTime = new UpdateDateTime(input)

        then:
        updateDateTime.timestamp() == expected

        where:
        description           | input                                   || expected
        "正常なLocalDateTime" | LocalDateTime.of(2026, 1, 18, 15, 45, 30) || LocalDateTime.of(2026, 1, 18, 15, 45, 30)
        "null"               | null                                    || null
    }

    @Unroll
    def "UpdateDateTimeの等価性: #description"() {
        given:
        def updateDateTime1 = new UpdateDateTime(input1)
        def updateDateTime2 = new UpdateDateTime(input2)

        expect:
        (updateDateTime1 == updateDateTime2) == expectedEqual
        (updateDateTime1.hashCode() == updateDateTime2.hashCode()) == expectedHashCodeEqual

        where:
        description              | input1                                   | input2                                   || expectedEqual | expectedHashCodeEqual
        "同じ値を持つ場合は等しい"   | LocalDateTime.of(2026, 1, 18, 15, 45, 30) | LocalDateTime.of(2026, 1, 18, 15, 45, 30) || true          | true
        "異なる値を持つ場合は等しくない" | LocalDateTime.of(2026, 1, 18, 15, 45, 30) | LocalDateTime.of(2026, 1, 18, 15, 45, 31) || false         | false
    }

    def "現在時刻でUpdateDateTimeを作成できる"() {
        when:
        def now = LocalDateTime.now()
        def updateDateTime = new UpdateDateTime(now)

        then:
        updateDateTime.timestamp() == now
    }
}

