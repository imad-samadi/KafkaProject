package com.example.Kafka;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Service

public class WikimediaChangesProducer {

    private static final Logger logger = LoggerFactory.getLogger(WikimediaChangesProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    private static final String TOPIC = "wikimedia_recentchange";
    private static final String URL = "https://stream.wikimedia.org/v2/stream/recentchange";

    @Scheduled(fixedRate = 5000)
    public void fetchAndProduce() {
        try {
            URI uri = new URI(URL);
            Scanner scanner = new Scanner(uri.toURL().openStream(), StandardCharsets.UTF_8);

            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                if (line.startsWith("data:")) {
                    String jsonData = line.substring(5).trim();
                    JSONObject jsonObject = new JSONObject(jsonData);


                    //logger.info("Raw Data: " + jsonData);

                    logger.info("Produced Message: " + jsonObject.toString());


                    kafkaTemplate.send(TOPIC, jsonObject.toString());
                    logger.info("Message sent to Kafka topic '{}' successfully.", TOPIC);
                }
            }

        } catch (Exception e) {
            logger.error("Error occurred while fetching or producing data", e);
        }
    }
}

