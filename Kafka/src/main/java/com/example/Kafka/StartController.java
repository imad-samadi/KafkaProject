package com.example.Kafka;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class StartController {

    private final   WikimediaChangesProducer wikimediaChangesProducer ;

    public StartController(WikimediaChangesProducer wikimediaChangesProducer) {
        this.wikimediaChangesProducer = wikimediaChangesProducer;
    }


    @GetMapping("/start")
    public void g(){
        this.wikimediaChangesProducer.fetchAndProduce();
    }
}
