package fr.lernejo.navy_battle.server.context;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Test {

    public HttpResponse<String> sendPOST (String body, String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        try {
            HttpResponse<String> response = client.send(requetePost,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (Exception e) {
            System.out.println("erreur response");
            return null;
        }
    }

    public HttpResponse<String> sendGET (String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .GET()
            .build();
        try {
            HttpResponse<String> response = client.send(requetePost,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (Exception e) {
            System.out.println("erreur response");
            return null;
        }
    }
}
