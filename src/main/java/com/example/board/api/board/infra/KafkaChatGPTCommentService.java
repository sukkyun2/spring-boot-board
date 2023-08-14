package com.example.board.api.board.infra;

import com.example.board.api.board.app.comment.AutoCommentAddService;
import com.example.board.api.board.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KafkaChatGPTCommentService implements AutoCommentAddService {
    private static final String TOPIC = "chatgpt";
    private final KafkaTemplate<String, ChatGPTCommentPayload> kafkaTemplate;

    @Override
    public void addComment(Board board) {
        Optional.of(board).map(this::createRecord).ifPresent(kafkaTemplate::send);
    }

    private ProducerRecord<String, ChatGPTCommentPayload> createRecord(Board board) {
        return new ProducerRecord<>(TOPIC, new ChatGPTCommentPayload(board));
    }
}
