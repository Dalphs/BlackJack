package sample;

import java.util.Collections;

public class BlackJack extends Graphics {
    public BlackJack() {
    }

    public void startGame() {
        players.add(new Player(100, 810, 300));
        players.add(new Player(100, 510, 400));
        players.add(new Player(100, 210, 300));

        defaultLayout();

        numberOfPlayers = players.size();
        fillArray(cards);
        Collections.shuffle(cards);
        copyArray();
        getValues();
        dealCardsValues();
        dealCards();
        createPlayerMarker();
        createCounters();
        bettingRound();

    }

}
