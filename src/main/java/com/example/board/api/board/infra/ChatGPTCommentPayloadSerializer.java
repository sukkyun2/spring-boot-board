package com.example.board.api.board.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class ChatGPTCommentPayloadSerializer implements Serializer<ChatGPTCommentPayload> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Serializer.super.configure(configs, isKey);
    }

    @Override
    @SneakyThrows
    public byte[] serialize(String topic, ChatGPTCommentPayload data) {
        if(data == null){
            return null;
        }

        return objectMapper.writeValueAsBytes(data);
    }

    @Override
    public byte[] serialize(String topic, Headers headers, ChatGPTCommentPayload data) {
        return Serializer.super.serialize(topic, headers, data);
    }

    @Override
    public void close() {
        Serializer.super.close();
    }
}
