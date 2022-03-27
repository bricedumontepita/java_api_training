package fr.lernejo.navy_battle.server.context;

import static org.junit.jupiter.api.Assertions.*;
import fr.lernejo.navy_battle.server.Server;
import java.net.http.HttpResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ApiGameStartHandlerTest extends fr.lernejo.navy_battle.server.context.Test {
    private final Server server = new Server(9876, "");
    private final Server server2 = new Server(8765, "http://localhost:9876");


    @Test
    void getTest() {
        this.server.run();
        HttpResponse<String> response = this.sendGET("http://localhost:9876/api/game/start");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("Page doesn't found"); // (3)
    }

    @Test
    void postTest() {
        HttpResponse<String> response = this.sendPOST("{body:'toto'}", "http://localhost:9876/api/game/start");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("Bad request");
    }

    @Test
    void postTest2() {
        HttpResponse<String> response = this.sendPOST("{\n" +
            "    \"id\": \"0c575465-21f6-43c9-8a2d-bc64c3ae6241\",\n" +
            "    \"message\": \"run\",\n" +
            "    \"url\": \"http://localhost:8795\"\n" +
            "}", "http://localhost:9876/api/game/start");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("{\"id\":\"9876\",\"url\":\"http://localhost:9876\",\"message\":\"reponse\"}");
    }

    @Test
    void postTest3() {
        HttpResponse<String> response = this.sendPOST("{\n" +
            "    \"id\": \"0c575465-21f6-43c9-8a2d-bc64c3ae6241\",\n" +
            "    \"message\": \"run\",\n" +
            "    \"url\": \"http://localhost:8795\"\n" +
            "    \"toto\": \"salut\"\n" +
            "}", "http://localhost:9876/api/game/start");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("Bad request");
    }

}
