package fr.lernejo.navy_battle.server.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.JsonHandler;
import fr.lernejo.navy_battle.NavyBattle;
import fr.lernejo.navy_battle.server.Server;
import fr.lernejo.navy_battle.server.request.FireRequest;
import fr.lernejo.navy_battle.server.request.StartRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiGameFireHandler extends ApiHandler implements HttpHandler {

    final NavyBattle game;
    final int port;
    final JsonHandler jsonHandler;
    final ObjectMapper mapper;

    public ApiGameFireHandler (NavyBattle game, int port) {
        this.game = game;
        this.port = port;
        this.jsonHandler = new JsonHandler();
        this.mapper = new ObjectMapper();
    }

    protected void getDamage (String area) {
        String consequence = this.game.getDamage();
        boolean shipLeft = this.game.hasShipLeft();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if ("GET".equals(requestMethod))
            this.manageGet(exchange);
        else
            this.manageOther(exchange);
    }

    public void manageGet(HttpExchange exchange) throws IOException {
        String query = exchange.getRequestURI().getQuery();
        ArrayList<String []> paramsValues = this.getParams(query);
        if (paramsValues == null || paramsValues.size() == 0) {
            this.response(400, "Bad request", exchange);
            return;
        }
        FireRequest response = new FireRequest();
        response.setConsequence(this.game.getDamage());
        response.setShipLeft( this.game.hasShipLeft());
        query = this.jsonHandler.toJson2(response, this.mapper);
        this.response(202, query, exchange);
    }

    public ArrayList<String []> getParams (String query) {
        if ("".equals(query) || query == null)
            return null;
        String [] params = query.split("&");
        ArrayList<String []> paramsValues = new ArrayList<>();
        for (int i = 0; i < params.length; i++) {
            String [] values = params[i].split("=");
            System.out.println(values);
            if ("cell".equals(values[0])) {
                paramsValues.add(values);
            }
        }
        return paramsValues;
    }
}
