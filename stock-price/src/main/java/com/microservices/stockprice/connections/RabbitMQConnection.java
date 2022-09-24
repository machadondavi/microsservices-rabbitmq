package com.microservices.stockprice.connections;

import contants.RabbitMQContants;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RabbitMQConnection {
    private static final String NOME_EXCHANGE = "amq_direct";
    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue fila(String nomeFila) {
        return new Queue(nomeFila, true, false, false);
    }

    private DirectExchange trocaDireta() {
        return new DirectExchange(NOME_EXCHANGE);
    }

    private Binding relacionamento(Queue fila, DirectExchange exchange) {
        return new Binding(fila.getName(), Binding.DestinationType.QUEUE, exchange.getName(), fila.getName(), null);
    }

    @PostConstruct
    private void adicionar() {
        Queue stockQueue = this.fila(RabbitMQContants.STOCK_QUEUE);
        Queue priceQueue = this.fila(RabbitMQContants.PRICE_QUEUE);

        DirectExchange exchange = this.trocaDireta();

        Binding bindingStock = this.relacionamento(stockQueue, exchange);
        Binding bindingPrice = this.relacionamento(priceQueue, exchange);

        //Criando as filas no RabbitMQ
        this.amqpAdmin.declareQueue(stockQueue);
        this.amqpAdmin.declareQueue(priceQueue);

        //Criando a exchange
        this.amqpAdmin.declareExchange(exchange);

        //Criando binding
        this.amqpAdmin.declareBinding(bindingStock);
        this.amqpAdmin.declareBinding(bindingPrice);
    }
}
