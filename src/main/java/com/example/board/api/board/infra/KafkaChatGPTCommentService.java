package com.example.board.api.board.infra;

import com.example.board.api.board.domain.Board;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaChatGPTCommentService implements ChatGPTCommentService {
    private static final String TOPIC = "chatgpt";
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    @SneakyThrows
    public void addComment(Board board) {
        JSONObject msgData = new JSONObject();
        msgData.put("seq", board.getSeq());
        msgData.put("title", board.getTitle());
        msgData.put("content", board.getContent());

        JSONObject msgObj = new JSONObject();
        msgObj.put("command", "request_chatgpt");
        msgObj.put("data", msgData);

        String msg = msgObj.toString(4);
        kafkaTemplate.send(TOPIC, msg);
    }
}
