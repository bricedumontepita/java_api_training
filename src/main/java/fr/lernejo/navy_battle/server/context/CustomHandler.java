package fr.lernejo.navy_battle.server.context;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;



public class CustomHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();

        String query = exchange.getRequestURI().getQuery();

        String body = "Hello";
        exchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = exchange.getResponseBody()) { // (1)
            os.write(body.getBytes());
        }
    }

}
