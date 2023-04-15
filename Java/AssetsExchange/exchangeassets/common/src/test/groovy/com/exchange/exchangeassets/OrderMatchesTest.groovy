package com.exchange.exchangeassets

import com.exchange.exchangeassets.common.OrderMatches
import spock.lang.Specification

import java.util.concurrent.ConcurrentLinkedQueue

class OrderMatchesTest extends Specification {

    def "GetMatches"() {
        given:
        OrderMatches orderMatches = new OrderMatches()

        when:
        def matches = orderMatches.getMatches()

        then:
        matches instanceof ConcurrentLinkedQueue
        matches.isEmpty()
    }

    def "AddMatch"() {
        given:
        OrderMatches orderMatches = new OrderMatches()
        UUID id = UUID.randomUUID()
        int numContracts = 10
        int executionPrice = 100

        when:
        orderMatches.addMatch(id as UUID, numContracts, executionPrice)

        then:
        orderMatches.getMatches().size() == 1
        def match = orderMatches.getMatches().peek()
        match.id() instanceof UUID
        match.numContracts() == numContracts
        match.executionPrice() == executionPrice
    }
}