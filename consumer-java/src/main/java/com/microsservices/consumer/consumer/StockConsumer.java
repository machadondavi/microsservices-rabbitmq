package com.microsservices.consumer.consumer;

import contants.RabbitMQContants;
import dto.StockDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StockConsumer {

    @RabbitListener(queues = RabbitMQContants.STOCK_QUEUE)
    private void consumer(StockDTO stockDTO) {
        System.out.println(stockDTO.codigoProduto);
        System.out.println(stockDTO.quantidade);
        System.out.println("=========================================================");
    }
}
