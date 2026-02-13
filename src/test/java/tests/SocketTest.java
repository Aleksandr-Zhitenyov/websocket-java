package tests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import model.SocketMessage;
import model.Subscribe;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import socket.SocketContext;
import socket.WebClient;

import java.util.Random;
import java.util.UUID;

import static io.restassured.RestAssured.given;

public class SocketTest {
    SocketContext context;

    public String getSocketConnectUrl() {
        String randomString = UUID.randomUUID().toString().substring(0, 8);

        Response response = given()
                .post("https://api.kucoin.com/api/v1/bullet-public")
                .then().log().body().extract()
                .response();
        String token = response.jsonPath().getString("data.token");
        String socketBaseEndpoint = response.jsonPath().getString("data.instanceServers[0].endpoint");
        return String.format("%s?token=%s&connectId=%s", socketBaseEndpoint, token, randomString);
    }

    @Test
    void socketKucoin() throws JsonProcessingException {
        Subscribe subscribe = new Subscribe();
        subscribe.setId(Math.abs(new Random().nextInt()));
        subscribe.setResponse(true);
        subscribe.setType("subscribe");
        subscribe.setTopic("/market/ticker:BTC-USDT");
        subscribe.setPrivateChannel(false);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(subscribe);

        context = new SocketContext();
        context.setTimeOut(5);
        context.setBody(json);
        context.setURI(getSocketConnectUrl());
        WebClient.getInstance().connectToSocket(context);

        String firstMessage = context.getMessageList().stream()
                .filter(msg -> msg.contains("\"type\":\"message\""))
                .findFirst().orElseThrow(() -> new RuntimeException("No message found"));
        SocketMessage socketMessageFirst = objectMapper.readValue(firstMessage, SocketMessage.class);

        String lastMessage = context.getMessageList().getLast();
        SocketMessage socketMessageLast = objectMapper.readValue(lastMessage, SocketMessage.class);

        Assertions.assertNotEquals(
                socketMessageFirst.getData().getSequence(),
                socketMessageLast.getData().getSequence());
    }
}
