package fr.lernejo.navy_battle.server.context;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiGameStartHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if ("POST".equals(requestMethod)) {
            this.managePost(exchange);
        } else {
            this.manageOther(exchange);
        }
    }

    public void managePost(HttpExchange exchange) throws IOException {
        String query = this.getContent(exchange);
        ObjectMapper mapper = new ObjectMapper();
        if (!this.isJson(query, mapper)) {
            exchange.sendResponseHeaders(400, "Bad Request".length());
            try (OutputStream os = exchange.getResponseBody()) { // (1)
                os.write("Bad Request".getBytes());
            }
            return;
        }
        Map<String, String> json = this.getJson(query, mapper);
        for (Map.Entry<String, String> pair : json.entrySet()) {
            System.out.println(String.format("Key (name) is: %s, Value (age) is : %s", pair.getKey(), pair.getValue()));
        }
        exchange.sendResponseHeaders(202, query.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(query.getBytes());
        }
    }

    public void manageOther(HttpExchange exchange) throws IOException {
        String body = "404 Page not found";
        exchange.sendResponseHeaders(404, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }

    protected String getContent(HttpExchange exchange) throws IOException {
        BufferedReader httpInput = new BufferedReader(new InputStreamReader(
            exchange.getRequestBody(), "UTF-8"));
        StringBuilder in = new StringBuilder();
        String input;
        while ((input = httpInput.readLine()) != null) {
            in.append(input).append(" ");
        }
        httpInput.close();
        return in.toString().trim();
    }

    protected Map<String, String> getJson (String json, ObjectMapper mapper) throws IOException {
        Map<String, String> scoreByName = mapper.readValue(json, Map.class);
        return scoreByName;
    }

    protected boolean isJson (String jsonInString, ObjectMapper mapper) {
        try {
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
