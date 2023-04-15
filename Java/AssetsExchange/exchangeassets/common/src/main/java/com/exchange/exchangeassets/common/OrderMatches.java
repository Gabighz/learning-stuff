package com.exchange.exchangeassets.common;

import java.util.UUID;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderMatches {
    private final ConcurrentLinkedQueue<Match> matches;

    public OrderMatches() {
        matches = new ConcurrentLinkedQueue<>();
    }

    public ConcurrentLinkedQueue<Match> getMatches() {
        return matches;
    }

    public void addMatch(UUID id, int numContracts, int executionPrice) {
        Match newMatch = new Match(id, numContracts, executionPrice);
        matches.add(newMatch);
    }

    public record Match(UUID id, int numContracts, int executionPrice) {
    }
}
