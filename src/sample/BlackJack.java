package sample;

import java.util.Collections;

public class BlackJack extends Graphics{
    public BlackJack() {
    }

    public void startGame(){
        fillArray(cards);
        Collections.shuffle(cards);
        copyArray();
        getValues();
        defaultLayout();
        dealCards();
    }
}
