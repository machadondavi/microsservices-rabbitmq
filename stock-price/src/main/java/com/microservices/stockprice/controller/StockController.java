package com.microservices.stockprice.controller;

import com.microservices.stockprice.service.RabbitMQService;
import contants.RabbitMQContants;
import dto.StockDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "estoque")
public class StockController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity alterarEstoque(@RequestBody StockDTO stockDTO) {
        System.out.println(stockDTO.codigoProduto);
        this.rabbitMQService.sendMessage(RabbitMQContants.STOCK_QUEUE, stockDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
