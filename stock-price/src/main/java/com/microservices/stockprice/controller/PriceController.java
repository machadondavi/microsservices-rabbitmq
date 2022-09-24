package com.microservices.stockprice.controller;

import com.microservices.stockprice.service.RabbitMQService;
import contants.RabbitMQContants;
import dto.PriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "preco")
public class PriceController {

    @Autowired
    private RabbitMQService rabbitMQService;

    @PutMapping
    private ResponseEntity alterarPreco(@RequestBody PriceDTO priceDTO) {
        System.out.println(priceDTO.codigoProduto);
        this.rabbitMQService.sendMessage(RabbitMQContants.PRICE_QUEUE, priceDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
