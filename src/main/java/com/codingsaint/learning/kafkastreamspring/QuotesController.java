package com.codingsaint.learning.kafkastreamspring;

import com.codingsaint.learning.kafkastreamspring.model.Quote;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuotesController {

    private QuotesProducer quotesProducer;

    public QuotesController(QuotesProducer quotesProducer) {
        this.quotesProducer = quotesProducer;
    }

    @GetMapping("produce/quote")
    public Quote produce(){
        return quotesProducer.quotes();
    }

}
