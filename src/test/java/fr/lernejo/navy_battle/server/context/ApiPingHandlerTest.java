package fr.lernejo.navy_battle.server.context;

import fr.lernejo.navy_battle.server.Server;
import java.net.http.HttpResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ApiPingHandlerTest extends fr.lernejo.navy_battle.server.context.Test {
    private final Server server = new Server(9876, "");

    @Test
    void check() {
        int number = 10;
        int other = 10;
        Assertions.assertThat(number).as("10 = 10")
            .isEqualTo(other);
    }

    @Test
    void ping() {
        this.server.run();
        HttpResponse<String> response = this.sendGET("http://localhost:9876/ping");
        Assertions.assertThat(
                response.body()).as("ping")
            .isEqualTo("OK"); // (3)
    }
}
