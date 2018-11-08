package sample;

import java.util.ArrayList;

public class Logic {
    //Classvariables that consists of two arraylists (cards and cards values) and an int (PlayerLastCard)
    private ArrayList<Integer> cards;
    private ArrayList<Integer> cardsValue = new ArrayList<>();
    private int playerLastCard = Main.getLastPlayerCard();

    //A constructor that create a new instance of the Logic class and assigns a deck of cards to the variable cards
    public Logic(ArrayList<Integer> cards) {
        this.cards = cards;
        copyArray();
    }

    //This method returns true if the game is finished, which is true if the player busts,
    // the dealer stands or the dealer busts
    public  boolean gameFinished(){
        if(playerBusted())
            return true;
        if(dealerBusted())
            return true;
        if(dealerStands())
            return true;

        return false;
    }

    //This method returns true if the player busts
    public boolean playerBusted(){
        return false;
    }

    //This method returns true if the dealer busts
    public boolean dealerBusted(){
       return false;
    }

    //This method returns true if the dealer has 17-21
    public boolean dealerStands(){
        return false;
    }

    //This method calculates the sum of the players' cards. We know the player has card index 0 and 2,
    // and all cards from index 4 to playersLastCard. Then we add the value of all those cards
    public int playerSum(){
        int sum = 0;
        sum += cardsValue.get(0) + cardsValue.get(2);
        for (int i = 4; i < playerLastCard; i++) {
            sum += cardsValue.get(i);
        }
        return sum;
    }

    //This method calculates the sum of the dealers cards. We know the dealer has card index 1 and 3.
    // If the dealer doesnt stand or bust as determined in other methods, this method will calculate the sum,
    //by adding the the cards that comes after the users last card untill the dealer either busts og stand
    public int dealerSum(){
        int index = playerLastCard + 1;
        int sum = 0;
        sum += cardsValue.get(1) + cardsValue.get(3);
        while(!dealerStands() && dealerBusted()){
            sum += cardsValue.get(index);
            index++;
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
        printCards();
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
        for (Integer i:cards) {
            System.out.println(i);
        }
    }


}
