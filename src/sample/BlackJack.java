package sample;

import java.util.Collections;

public class BlackJack extends Graphics {
    int[] playerCoordinates = {840, 300, 540, 400, 240, 300};
    public BlackJack() {
    }

    public void startGame() {
        players.add(new Player(100, 810, 300));
        players.add(new Player(100, 510, 400));
        players.add(new Player(100, 210, 300));
        players.add(new Dealer());

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

    }

}
