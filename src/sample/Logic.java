package sample;

import java.util.ArrayList;
import java.util.Collections;

public class Logic extends Game{
    //Classvariables that consists of two arraylists (cards and cards values) and an int (PlayerLastCard)
    private ArrayList<Integer> cardsValue = new ArrayList<>();
    protected int dealerNumberOfHits = 0;
    protected int dealerSum;
    int playerSum = 0;

    //A constructor that create a new instance of the Logic class and assigns a deck of cards to the variable cards
    public Logic() {
    }


    //This method returns true if the player busts
    public boolean playerBusted(){
        playerSum();
        if(playerSum > 21)
            return true;
        return false;
    }

    //This method returns true if the dealer busts
    public boolean dealerBusted(){
       if(dealerSum > 21)
           return true;
        return false;
    }

    //This method returns true if the dealer has 17-21
    public boolean dealerStands(){
        if(dealerSum >= 17 && dealerSum <= 21)
            return true;
        return false;
    }

    //This method calculates the sum of the players' cards. We know the player has card index 0 and 2,
    // and all cards from index 4 to playersLastCard. Then we add the value of all those cards
    public void playerSum(){
        if (playerSum < cardsValue.get(0) + cardsValue.get(2))
            playerSum += cardsValue.get(0) + cardsValue.get(2);

        for (int i = 4; i < lastCard; i++) {
            playerSum += cardsValue.get(i);
        }
    }

    //This method calculates the sum of the dealers cards. We know the dealer has card index 1 and 3.
    // If the dealer doesnt stand or bust as determined in other methods, this method will calculate the sum,
    //by adding the the cards that comes after the users last card untill the dealer either busts og stand
    public int dealerSum(){
        int index = lastCard;
        int sum = cardsValue.get(1) + cardsValue.get(3);
        while(sum < 17){
            sum += cardsValue.get(index);
            System.out.println("index: " + index);
            System.out.println("CardValue" + cardsValue.get(index));
            index++;
            dealerNumberOfHits++;
        }
        return sum;
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
