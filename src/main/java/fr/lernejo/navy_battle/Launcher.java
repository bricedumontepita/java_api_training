
package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.server.Server;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class Launcher {
    public static void main(String[] args) {
        if (args.length <= 0) {
            System.out.println("Usage: ./prog port_number\n");
            return;
        }
        int port = 0;
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Usage: ./prog port_number. Ex: ./prog 8000\n");
            return;
        }

        Server server = new Server();
        server.run(port);
    }
}