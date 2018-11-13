package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import java.util.Collections;

public class BlackJack extends Graphics {
    int[] playerCoordinates = {640, 300, 440, 400, 240, 300};
    public BlackJack() {
    }

    public void startGame() {
        //Calls the startbutton method in the Graphics class.
        players.add(new Player(100, 640, 300));
        players.add(new Player(100, 440, 400));
        players.add(new Player(100, 240, 300));
        players.add(new Dealer());

        defaultLayout();

        numberOfPlayers = players.size();
        fillArray(cards);
        copyArray();
        getValues();
        dealCards();

    }
}
