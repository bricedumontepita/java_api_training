package fr.lernejo.navy_battle;

import static org.junit.jupiter.api.Assertions.*;
import fr.lernejo.navy_battle.server.Server;
import java.net.http.HttpResponse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class LauncherTest {
    private final Launcher game = new Launcher();

    @Test
    void params() {
        String [] args = new String[0];
        boolean result = game.checkArgs(args);
        Assertions.assertThat(result).as("get")
            .isEqualTo(false);
    }

    @Test
    void params2() {
        String [] args = new String[1];
        args[0] = "12";
        boolean result = game.checkArgs(args);
        Assertions.assertThat(result).as("get")
            .isEqualTo(true);
    }

    @Test
    void params3() {
        String [] args = new String[1];
        args[0] = "pelican";
        boolean result = game.checkArgs(args);
        Assertions.assertThat(result).as("get")
            .isEqualTo(false);
    }
}
