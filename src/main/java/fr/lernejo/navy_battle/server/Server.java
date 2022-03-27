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
    final String url;

    public Server (int port, String url) {
        this.port = port;
        this.url = url;
    }

    public void run () {
        try {
            System.out.println("Run");
            HttpServer server = HttpServer.create(new InetSocketAddress(this.port), 0);
            server.setExecutor(Executors.newFixedThreadPool(1));

            NavyBattle game = new NavyBattle();
            server.createContext("/ping", new ApiPingHandler());
            server.createContext("/api/game/start", new ApiGameStartHandler(this.port));
            server.createContext("/api/game/fire", new ApiGameFireHandler(game, this.port));
            server.start();

            if (!"".equals(this.url)) {
                this.sendMessage();
            }

        } catch (IOException e) {
            System.out.println("Erreur creation serveur " + e);
        }
    }

    public void sendMessage () {
        System.out.println("Send");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requetePost = HttpRequest.newBuilder()
            .uri(URI.create(this.url + "/api/game/start"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString("{\"id\":\""+this.port+"\", \"url\":\"http://localhost:" + this.port + "\", \"message\":\"hello\"}"))
            .build();

        try {
            HttpResponse<String> response = client.send(requetePost,
                HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        } catch (Exception e) {
            System.out.println("erreur response");
        }
    }
}
