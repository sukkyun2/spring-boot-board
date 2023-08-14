package com.example.board.api.board.infra;

import com.example.board.api.board.domain.Board;
import com.example.board.api.board.domain.BoardStatus;
import com.example.board.api.common.domain.Reg;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.KafkaException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@SpringBootTest(properties = "spring.kafka.producer.bootstrap-servers=${spring.embedded.kafka.brokers}")
@EmbeddedKafka
@Slf4j
class KafkaChatGPTCommentIT {
    private static final String TOPIC = "chatgpt";

    @Autowired
    private EmbeddedKafkaBroker kafkaBroker;
    @Autowired
    private KafkaChatGPTCommentService kafkaChatGPTCommentService;
    private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;
    private KafkaMessageListenerContainer<String, String> container;

    @BeforeEach
    void setUp() {
        consumerRecords = new LinkedBlockingQueue<>();
        ContainerProperties containerProperties = new ContainerProperties(TOPIC);
        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("sender", "false", kafkaBroker);
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties);

        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);
        container.setupMessageListener((MessageListener<String, String>) record -> consumerRecords.add(record));

        container.start();

        ContainerTestUtils.waitForAssignment(container, kafkaBroker.getPartitionsPerTopic());
    }

    @AfterEach
    void tearDown() {
        container.stop();
    }

    @Test
    @DisplayName("kafka publish event test")
    void when_board_created_then_publish_event() throws InterruptedException {
        Board givenCreatedBoard = new Board(1, "제목", "내용", Collections.emptyList(), BoardStatus.NOT_DELETED, 0, Reg.of(1), null);

        kafkaChatGPTCommentService.addComment(givenCreatedBoard);

        ConsumerRecord<String, String> record = consumerRecords.poll(5L, TimeUnit.SECONDS);
        ChatGPTCommentPayload payload = extractPayload(record);

        if (record == null) {
            fail("consumer did not receive the event");
        }

        assertThat(record.topic()).isEqualTo(TOPIC);
        assertThat(payload.commend()).isEqualTo("request_chatgpt");

        ChatGPTCommentPayload.ChatGPTCommentPayloadData data = payload.data();
        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(data.seq()).isEqualTo(givenCreatedBoard.getSeq());
            softly.assertThat(data.title()).isEqualTo(givenCreatedBoard.getTitle());
            softly.assertThat(data.content()).isEqualTo(givenCreatedBoard.getContent());
        });
    }

    private ChatGPTCommentPayload extractPayload(ConsumerRecord<String, String> record) {
        return Optional.ofNullable(record).map(this::deserialize).orElseThrow(KafkaException::new);
    }

    @SneakyThrows
    private ChatGPTCommentPayload deserialize(ConsumerRecord<String, String> record) {
        return new ObjectMapper().readValue(record.value(), ChatGPTCommentPayload.class);
    }
}