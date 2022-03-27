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
import fr.lernejo.navy_battle.server.Server;
import fr.lernejo.navy_battle.server.request.StartRequest;

public class ApiGameStartHandler extends ApiHandler implements HttpHandler {
    final int port;
    final JsonHandler jsonHandler;
    final ObjectMapper mapper;
    final Server server;

    public ApiGameStartHandler (int port, Server server) {
        this.port = port;
        this.server = server;
        this.jsonHandler = new JsonHandler();
        this.mapper = new ObjectMapper();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        System.out.println("got");
        String requestMethod = exchange.getRequestMethod();
        if ("POST".equals(requestMethod))
            this.managePost(exchange);
        else
            this.manageOther(exchange);
    }

    public void managePost(HttpExchange exchange) throws IOException {
        String query = this.getContent(exchange);
        StartRequest requestData = (StartRequest) this.jsonHandler.parseJson(query, StartRequest.class, mapper);
        if (requestData == null) {
            this.response(400, "Bad request", exchange);
            return;
        }
        StartRequest response = new StartRequest();
        response.setId(Integer.toString(this.port));
        response.setUrl( "http://localhost:" + this.port);
        response.setMessage("reponse");
        this.response(202, this.jsonHandler.toJson2(response, this.mapper), exchange);
        if ("".equals(this.server.getUrl())) {
            this.server.setUrl(requestData.getUrl());
            this.server.sendGET(this.server.getUrl() + "/api/game/fire?cell=C7");
        }
    }
}
