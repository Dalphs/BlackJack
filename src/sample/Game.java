package sample;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    //Class variables
    //Arraylist for storing cards
    public static ArrayList<Integer> cards = new ArrayList<>();
    //Int to keep track of the current index for the cards arraylist
    public static int cardIndex = 0;
    //Int that keeps trac of the players last hit
    public static int lastCard = 3;

    public Game() {
    }

    /*public void startGame() {
        cardIndex = 0;

        //We use the fillArray method on the cards arraylist and then shuffling it
        //using the shuffle method from the Collections class
        fillArray(cards);
        Collections.shuffle(cards);

        //This method changes the cards from specific cards to the general values for the cards
        game.getValues();
    }*/

    //This method fills the cards arraylist with the numbers from 1-52,
    // which will represent all cards in a deck of cards
    //1-13 is spades, 14-26 is hearts, 27-39 is diamonds and 40-52 is clubs
    public static void fillArray(ArrayList<Integer> list){
        for (int i = 1; i <= 52; i++) {
            list.add(i);
        }
    }

}

