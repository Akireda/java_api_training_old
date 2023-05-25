package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;

public class FireHttpHandler implements HttpHandler {

    private final Server srv;

    public FireHttpHandler(Server srv) {
        this.srv = srv;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        if ("GET".equals(method)) {
            JSONObject response = new JSONObject()
                .put("consequence", "sunk")
                .put("shipLeft", true);
            sendResponse(exchange, response.toString(), 200);
        } else {
            sendResponse(exchange, "BAD REQUEST", 400);
        }
    }

    public void sendResponse(HttpExchange exchange, String response, int code) throws IOException {
        exchange.getResponseHeaders().set("Accept", "application/json");
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        byte[] responseBytes = response.getBytes();
        exchange.sendResponseHeaders(code, responseBytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseBytes);
        }
    }
}
