package fr.lernejo.navy_battle;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class StartHttpHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            checkJsonAndRespond(exchange);
        } else {
            exchange.sendResponseHeaders(404, "NOT FOUND".length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write("NOT FOUND".getBytes());
            }
        }
    }

    private boolean checkJson(JSONObject jsonData) {
        try {
            InputStream schemaStream = getClass().getResourceAsStream("/startJsonSchema.json");
            JSONObject jsonSchema = new JSONObject(new JSONTokener(schemaStream));
            Schema schemaValidator = SchemaLoader.load(jsonSchema);
            schemaValidator.validate(jsonData);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void checkJsonAndRespond(HttpExchange exchange) throws IOException {
        try (InputStream requestBody = exchange.getRequestBody();
             InputStreamReader requestBodyReader = new InputStreamReader(requestBody);
             BufferedReader reader = new BufferedReader(requestBodyReader)) {
            StringBuilder requestJson = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                requestJson.append(line);
            }
            JSONObject jsonData = new JSONObject(requestJson.toString());

            if (!checkJson(jsonData)) {
                sendBadRequestResponse(exchange);
            } else {
                sendSuccessResponse(exchange);
            }
        }
    }

    private void sendBadRequestResponse(HttpExchange exchange) throws IOException {
        String response = "BAD REQUEST";
        exchange.sendResponseHeaders(400, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }

    private void sendSuccessResponse(HttpExchange exchange) throws IOException {
        String response = "{\"id\":\"1\", \"url\":\"http://localhost:" + exchange.getLocalAddress().getPort() + "\", \"message\":\"Good luck!\"}";
        exchange.sendResponseHeaders(202, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
        System.out.println("Client connected");
    }

}
