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
        if ("POST".equals(requestMethod))
            this.managePost(exchange);
        else
            this.manageOther(exchange);
    }

    public void managePost(HttpExchange exchange) throws IOException {
        System.out.println("Got");
        String query = this.getContent(exchange);
        Map<String, String> json = this.jsonHandler.isJson(query, this.mapper);
        if (json == null) {
            this.response(400, "Bad request", exchange);
            return;
        }
        json.replace("id", Integer.toString(this.port));
        json.replace("url", "http://localhost:" + this.port);
        json.replace("message", "reponse");
        query = this.jsonHandler.toJson(json, this.mapper);
        this.response(202, query, exchange);
    }

    public void manageOther(HttpExchange exchange) throws IOException {
        this.response(404, "Page doesn't found", exchange);
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

    protected void response (int code, String body, HttpExchange exchange) throws IOException{
        exchange.sendResponseHeaders(code, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

}
