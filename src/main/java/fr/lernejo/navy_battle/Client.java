package fr.lernejo.navy_battle;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    protected final HttpClient client;
    protected final int port;
    protected final String servUrl;

    public Client(int port, String url) {
        this.client = HttpClient.newHttpClient();
        this.servUrl = url;
        this.port = port;
    }

    public int startGame() throws IOException, InterruptedException {
        String requestBody = "{\"id\":\"1\", \"url\":\"http://localhost:" + this.port + "\", \"message\":\"Bonjour_toi\"}";
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(this.servUrl + "/api/game/start"))
            .header("Accept", "application/json")
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode();
    }
}
