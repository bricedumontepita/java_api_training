package fr.lernejo.navy_battle.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.NavyBattle;
import fr.lernejo.navy_battle.server.context.ApiGameFireHandler;
import fr.lernejo.navy_battle.server.context.ApiGameStartHandler;
import fr.lernejo.navy_battle.server.context.ApiPingHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;

public class Server {
    final int port;
    final StringBuilder url;
    final int[] limit = new int[1];

    public String getUrl() {
        return url.toString();
    }

    public void setUrl (String url) {
        this.url.replace(0, this.url.length(), url);
    }

    public Server (int port, String url) {
        this.port = port;
        this.url = new StringBuilder(url);
        this.limit[0] = 50;
    }

    public void run () {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
            server.setExecutor(Executors.newFixedThreadPool(1));
            NavyBattle game = new NavyBattle();
            server.createContext("/ping", new ApiPingHandler());
            server.createContext("/api/game/start", new ApiGameStartHandler(this.port, this));
            server.createContext("/api/game/fire", new ApiGameFireHandler(game, this.port, this));
            server.start();
            if (!"".equals(this.url.toString()))
                this.sendMessage("");
        } catch (IOException e) {
            System.out.println("Erreur creation serveur " + e);
        }
    }

    public void sendMessage (String body) {
        if ("".equals(body))
            body = "{\"id\":\""+this.port+"\", \"url\":\"http://localhost:" + this.port + "\", \"message\":\"hello\"}";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(this.url.toString() + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(body)).build();
        try {
            HttpResponse<String> response = client.send(requetePost, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.out.println("erreur response");
        }
    }

    public HttpResponse<String> sendGET (String url) {
        if (this.limit[0]-- <= 0)
            return null;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json").GET().build();
        try {
            HttpResponse<String> response = client.send(requetePost,
                HttpResponse.BodyHandlers.ofString());
            return response;
        } catch (Exception e) {
            return null;
        }
    }
}
