package com.exchange.exchangeassets.tradingengine;

import com.exchange.exchangeassets.common.OrderMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonPropertyOrder({"order_id", "matches"})
public class OrderSubmissionResponse {
    @JsonProperty("order_id")
    private String orderId;

    @JsonSerialize(using = OrderMatchesSerializer.class)
    private OrderMatches matches;

    public OrderSubmissionResponse(String orderId, OrderMatches matches) {
        this.orderId = orderId;
        this.matches = matches;
    }

}
