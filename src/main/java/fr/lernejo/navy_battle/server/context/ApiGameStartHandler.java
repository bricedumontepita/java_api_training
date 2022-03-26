package fr.lernejo.navy_battle.server.context;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.navy_battle.JsonHandler;

public class ApiGameStartHandler implements HttpHandler {
    final int port;
    final JsonHandler jsonHandler;
    final ObjectMapper mapper;

    public ApiGameStartHandler (int port) {
        this.port = port;
        this.jsonHandler = new JsonHandler();
        this.mapper = new ObjectMapper();
    }

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
        Map<String, String> json = this.jsonHandler.isJson(query, this.mapper);
        if (json == null) {
            exchange.sendResponseHeaders(400, "Bad Request".length());
            try (OutputStream os = exchange.getResponseBody()) { // (1)
                os.write("Bad Request".getBytes());
            }
            return;
        }
        json.replace("id", Integer.toString(this.port));
        json.replace("url", "http://localhost:" + this.port);
        json.replace("message", "reponse");
        query = this.jsonHandler.toJson(json, this.mapper);
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


}
