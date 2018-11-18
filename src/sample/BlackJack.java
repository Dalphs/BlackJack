package sample;

import java.util.Collections;

public class BlackJack extends Graphics {
    public BlackJack() {
    }

    public void startGame() {
        players.add(new Player(100, 810, 300));
        players.add(new Player(100, 510, 400));
        players.add(new Player(100, 210, 300));

        numberOfPlayers = players.size();
        startButton();

    }

}
