package sample;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;

public class Logic extends Game{
    //Classvariables that consists of an arraylist, to hold the value of the cards,
    // and thee ints to count how many times the dealer hits, and hte dealers and players sum
    protected ArrayList<Integer> cardsValue = new ArrayList<>();
    Text playerCount;

    //A constructor that create a new instance of the Logic class and assigns a deck of cards to the variable cards
    public Logic() {
    }

    public void dealCardsValues(){
        for (int i = 0; i < numberOfPlayers * 2 + 2; i++) {
            if(i % (numberOfPlayers + 1) == numberOfPlayers){
                dealer.addCard(cards.get(i), cardsValue.get(i));
            }else {
                players.get(i % (numberOfPlayers + 1)).addCard(cards.get(i), cardsValue.get(i));
                System.out.println("Deal value card " + i + " is: " + cardsValue.get(i));
            }
        }
    }

    public void reward (int status, Player a){
        System.out.println("----------------------------------------");
        switch (status){
            case 1:
                a.setMoney(a.getMoney() + a.getCurrentBet());break;
            case 2:
                a.setMoney(a.getMoney() - a.getCurrentBet()); break;
            case 3:
                a.setMoney(a.getMoney() + a.getCurrentBet() * 1.5); break;
            default:
                System.out.println("Draw"); break;
        }
        a.setCurrentBet(0);
    }


    //This method updates the classvariable playersum returns true if the player busts
    public boolean isBusted(int sum){
        if(sum > 21)
            return true;
        return false;
    }

    public boolean allPlayersBusted(){
        for (int i = 0; i < numberOfPlayers; i++) {
           if (!players.get(i).isBusted())
               return false;
        }
        return true;
    }

    public void resetLogic(){
        cardIndex = 0;
        //We clear the arraylists with cards and cardsValues as well as the pane that displays out game
        cardsValue.clear();
        cards.clear();
    }

    //This metohod changes the specific cards to values. Say you have the king of spades and queen of hearts
    //for finding the total amount we only need to know they're worth 10 points each
    public void getValues(){
        //This for-loop changes all 10, jacks, queens, kings and aces of all suits to the value of 10.
        //It adds 10 to the same index as the card, and then delete the card from the array by deleting it from
        //the original index + 1
            for (int j = 0; j < 52; j++) {
                if(cardsValue.get(j) % 13 == 0 || cardsValue.get(j) % 13 == 12 || cardsValue.get(j) % 13 == 11 || cardsValue.get(j) % 13 == 10) {
                    cardsValue.add(j, 10);
                    cardsValue.remove(j + 1);
                }
            }
        //The outer for-loop determines whether we are looking for an ace, 2, 3, 4, 5, 6, 7, 8 or 9 of any suit.
        //As above it adds the corresponding value tho the array in the same index as the original card
        // and then deletes the original card from its new position
        for (int i = 1; i <= 9 ; i++) {
            for (int j = 0; j < 52; j++) {
                if (cardsValue.get(j) % 13 == i){
                    cardsValue.add(j, i);
                    cardsValue.remove(j + 1);
                }
            }
        }
    }


    //Method that copies cards to cardsValue array, so I can change the values and not change the original deck of cards
    public void copyArray(){
        for (int i = 0; i < 52 ; i++ ){
            cardsValue.add(cards.get(i));
        }
    }


   //Method that prints all values for the cards in the deck and the specific cards
    public void printCards(){
        for (Integer i: cardsValue) {
            System.out.println(i);
        }
        System.out.println("------------------------------------------------------------------");
        for (Integer i: cards) {
            System.out.println(i);
        }
    }


}
