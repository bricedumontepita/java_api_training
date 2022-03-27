package fr.lernejo.navy_battle.server.request;

public class FireRequest {
    final StringBuilder consequence = new StringBuilder();
    final boolean[] shipLeft = new boolean[1];

    public StringBuilder getConsequence() {
        return this.consequence;
    }

    public boolean getShipLeft () {
        return this.shipLeft[0];
    }

    public void setConsequence(String consequence) {
        this.consequence.replace(0, this.consequence.length(), consequence);
    }

    public void setShipLeft(boolean shipLeft) {
        this.shipLeft[0] = shipLeft;
    }

}
