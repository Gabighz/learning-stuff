package com.exchange.exchangeassets.tradingengine

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MvcResult
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.jayway.jsonpath.JsonPath

@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = TradingEngine.class)
class TradingEngineControllerTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    String templateBuy = '''{
                    "side": "BUY",
                    "type": "LIMIT",
                    "numContracts": 10,
                    "limitPrice": 100,
                    "currency": "USD"
                }'''

    @Unroll
    def "GetOrdersReceived"() {
        given:
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content(templateBuy))

        expect:
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$').isNotEmpty())
    }

    @Unroll
    def "SubmitOrder"() {
        expect:
        mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content(templateBuy))
                .andExpect(status().isCreated())
                .andExpect(jsonPath('matches').isArray())
    }

    @Unroll
    def "CancelPendingOrder"() {
        given:
        MvcResult result = mockMvc.perform(post("/orders")
                .contentType("application/json")
                .content(templateBuy))
                .andExpect(status().isCreated())
                .andExpect(jsonPath('matches').isArray())
                .andReturn()

        String returnedJson = result.getResponse().getContentAsString()
        String orderId = JsonPath.read(returnedJson, '$.order_id')

        when:
        mockMvc.perform(delete("/orders/{id}", orderId))
                .andExpect(status().isOk())

        then:
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath('$').isArray())
                .andExpect(jsonPath('$[?(@.id == \'' + orderId + '\')]').doesNotExist())
    }
}