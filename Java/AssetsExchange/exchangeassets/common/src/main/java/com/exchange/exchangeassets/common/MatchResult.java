package com.exchange.exchangeassets.common;

import org.springframework.data.util.Pair;

import java.util.List;

public record MatchResult(List<Order> matchedOrders, List<Pair<Integer, Integer>> filledContracts) {
}

