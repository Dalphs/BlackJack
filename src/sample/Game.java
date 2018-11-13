package sample;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    //Class variables
    //Arraylist for storing cards
    public static ArrayList<Integer> cards = new ArrayList<>();
    //Int to keep track of the current index for the cards arraylist
    public static int cardIndex;
    //Int that keeps trac of the players last hit
    public static int lastCad;
    ArrayList<Actors> players = new ArrayList<>();
    public static int numberOfPlayers = 0;
    public static int playerTurn = 0;

    public Game() {
    }

    //This method fills the cards arraylist with the numbers from 1-52,
    // which will represent all cards in a deck of cards
    //1-13 is spades, 14-26 is hearts, 27-39 is diamonds and 40-52 is clubs
    public static void fillArray(ArrayList<Integer> list){
        for (int i = 1; i <= 52; i++) {
            list.add(i);
        }
    }

}

