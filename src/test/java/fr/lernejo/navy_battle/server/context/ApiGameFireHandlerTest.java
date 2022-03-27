

package fr.lernejo.navy_battle.server.context;

import static org.junit.jupiter.api.Assertions.*;
import fr.lernejo.navy_battle.server.Server;
import java.net.http.HttpResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ApiGameFireHandlerTest extends fr.lernejo.navy_battle.server.context.Test {
    private final Server server = new Server(9876, "");
    private final Server server2 = new Server(8765, "");


    @org.junit.jupiter.api.Test
    void getTest() {
        this.server.run();
        HttpResponse<String> response = this.sendGET("http://localhost:9876/api/game/fire");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("Bad request");
    }

    @org.junit.jupiter.api.Test
    void getTest2() {
        this.server.run();
        HttpResponse<String> response = this.sendGET("http://localhost:9876/api/game/fire?toto=tata");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("Bad request");
    }

    @org.junit.jupiter.api.Test
    void getTest3() {
        this.server.run();
        HttpResponse<String> response = this.sendGET("http://localhost:9876/api/game/fire?toto=tata&cell=XX");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("{\"consequence\":\"miss\",\"shipLeft\":true}");
    }

    @org.junit.jupiter.api.Test
    void postTest() {
        HttpResponse<String> response = this.sendPOST("{body:'toto'}", "http://localhost:9876/api/game/fire");
        Assertions.assertThat(
                response.body()).as("get")
            .isEqualTo("Page doesn't found");
    }
}
