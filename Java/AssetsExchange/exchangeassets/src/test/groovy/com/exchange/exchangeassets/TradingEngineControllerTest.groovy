package com.exchange.exchangeassets

import com.exchange.exchangeassets.orders.Enums.Currency
import com.exchange.exchangeassets.orders.Enums.OrderSide
import com.exchange.exchangeassets.orders.Enums.OrderType
import com.exchange.exchangeassets.orders.Order
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = ExchangeassetsApplication.class)
class TradingEngineControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    @Unroll
    def "GetOrdersReceived"() {
        given:
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content('''{
                    "side": "BUY",
                    "type": "LIMIT",
                    "numContracts": 10,
                    "limitPrice": 100,
                    "currency": "USD"
                }'''))

        expect:
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$').isNotEmpty())
    }

    @Unroll
    def "SubmitOrder"() {
        given:
        Order order = new Order(OrderSide.BUY, OrderType.LIMIT, 10, 100, Currency.USD)

        expect:
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content('''{
                    "side": "BUY",
                    "type": "LIMIT",
                    "numContracts": 10,
                    "limitPrice": 100,
                    "currency": "USD"
                }'''))
                .andExpect(status().isCreated())
                .andExpect(jsonPath('matches').isArray())
    }
}