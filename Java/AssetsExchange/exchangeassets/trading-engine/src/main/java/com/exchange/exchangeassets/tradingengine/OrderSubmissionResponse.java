package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.OrderMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.UUID;

@JsonPropertyOrder({"order_id", "matches"})
class OrderSubmissionResponse {
    @JsonProperty("order_id")
    private UUID orderId;

    @JsonSerialize(using = OrderMatchesSerializer.class)
    private OrderMatches matches;

    OrderSubmissionResponse(UUID orderId, OrderMatches matches) {
        this.orderId = orderId;
        this.matches = matches;
    }
}
