package com.example.Consumer;


import jakarta.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class WikimediaConsumer {

    private WikimediaChangeRepository repository;

    public WikimediaConsumer(WikimediaChangeRepository repository) {
        this.repository = repository;
    }

    private static final Logger logger = LoggerFactory.getLogger(WikimediaConsumer.class);

    @KafkaListener(topics = "wikimedia_recentchange", groupId = "wikimedia-group")
    public void consume(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);

            WikimediaChange change = new WikimediaChange();
            change.setWiki(jsonObject.getString("wiki"));
            change.setTitle(jsonObject.getString("title"));
            change.setUser(jsonObject.getString("user"));
            change.setComment(jsonObject.optString("comment", ""));
            change.setNotify_url(jsonObject.optString("notify_url", ""));
            change.setBot(jsonObject.optBoolean("bot", false));

            repository.save(change);
            logger.info("Saved to DB: " + change);
        } catch (Exception e) {
            logger.error("Error saving to DB", e);
        }
    }
}
