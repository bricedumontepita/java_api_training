package fr.lernejo.navy_battle;

import java.util.HashMap;
import java.util.Map;

public class NavyBattle {
    final Map<Integer, Integer> ships;

    public NavyBattle () {
        this.ships = new HashMap<Integer, Integer>();
        this.ships.put(5, 1);
        this.ships.put(4, 1);
        this.ships.put(3, 2);
        this.ships.put(2, 1);
    }

    public boolean hasShipLeft () {
        for (Map.Entry<Integer, Integer> pair : this.ships.entrySet()) {
            if (pair.getValue() >= 1)
                return true;
        }
        return false;
    }

    public String getDamage () {
        return "miss";
    }
}
