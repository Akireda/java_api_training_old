package fr.lernejo.navy_battle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


class StartHttpHandlerTest {

    @Test
    void handleWrongMethod() throws IOException, InterruptedException {
        Server server = new Server(9878, "localhost");
        server.init();
        Server client = new Server(8769, "localhost","http://localhost:9876");
        client.init();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9878/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET().build();
        HttpResponse response = client.getClt().client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    void handleNotValid() throws IOException, InterruptedException {
        Server server = new Server(9880, "localhost");
        server.init();
        Server client = new Server(8781, "localhost","http://localhost:9880");
        client.init();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9880/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"osef\":\"1\", \"test\":\"http://localhost:8781\", \"message\":\"hello\"}"))
            .build();

        HttpResponse response = client.getClt().client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(400, response.statusCode());
    }

    @Test
    void handleValid() throws IOException, InterruptedException {
        Server server = new Server(9882, "localhost");
        server.init();
        Server client = new Server(8783, "localhost","http://localhost:9882");
        client.init();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:9880/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\"1\", \"url\":\"http://localhost:8783\", \"message\":\"hello\"}"))
            .build();

        HttpResponse response = client.getClt().client.send(request, HttpResponse.BodyHandlers.ofString());
        Assertions.assertEquals(202, response.statusCode());
    }

}
