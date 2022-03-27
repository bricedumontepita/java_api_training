package fr.lernejo.navy_battle.server.context;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ApiHandler {

    public void manageOther(HttpExchange exchange) throws IOException {
        this.response(404, "Page doesn't found", exchange);
    }

    protected void response (int code, String body, HttpExchange exchange) throws IOException{
        exchange.getResponseHeaders().set("Content-type", "application/json");
        exchange.sendResponseHeaders(code, body.length());
        try (OutputStream os = exchange.getResponseBody()) {
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
