
package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.server.Server;

//import com.fasterxml.jackson.databind.ObjectMapper;

public class Launcher {

    public static boolean checkArgs (String[] args) {
        int port = 0;
        String url = "";
        if (args.length <= 0) {
            System.out.println("Usage: ./prog port_number\n");
            return false;
        }
        try {
            port = Integer.parseInt(args[0]);
        } catch (NumberFormatException nfe) {
            System.out.println("Usage: ./prog port_number. Ex: ./prog 8000\n");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        if (checkArgs(args) == false)
            return;
        Server server = new Server(Integer.parseInt(args[0]), args.length >= 2 ? args[1] : "");
        server.run();
    }
}
