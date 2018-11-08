package sample;

import java.util.ArrayList;

public class Logic {
    private ArrayList<Integer> cards;
    private ArrayList<Integer> cardsValue = new ArrayList<>();
    private int playerLastCard = Main.getLastPlayerCard();

    public Logic(ArrayList<Integer> cards) {
        this.cards = cards;
        copyArray();
    }

    public  boolean gameFinished(){
        if(playerBusted())
            return true;
        if(dealerBusted())
            return true;
        if(dealerStands())
            return true;

        return false;
    }

    public boolean playerBusted(){
        return false;
    }

    public boolean dealerBusted(){
       return false;
    }

    public boolean dealerStands(){
        return false;
    }

    public int playerSum(){
        int sum = 0;
        sum += cardsValue.get(0) + cardsValue.get(2);
        for (int i = 4; i < playerLastCard; i++) {
            sum += cardsValue.get(i);
        }
        return sum;
    }

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

    public void getValues(){
        printCards();
        System.out.println("-----------------------------------------------");

            for (int j = 0; j < 52; j++) {
                if(cardsValue.get(j) % 13 == 0 || cardsValue.get(j) % 13 == 12 || cardsValue.get(j) % 13 == 11 || cardsValue.get(j) % 13 == 10) {
                    cardsValue.add(j, 10);
                    cardsValue.remove(j + 1);
                }
            }
        for (int i = 1; i <= 9 ; i++) {
            for (int j = 0; j < 52; j++) {
                if (cardsValue.get(j) % 13 == i){
                    cardsValue.add(j, i);
                    cardsValue.remove(j + 1);
                }
            }
        }

        for (Integer i:cardsValue) {
            System.out.println(i);
        }
    }

    public void copyArray(){
        for (int i = 0; i < 52 ; i++ ){
            cardsValue.add(cards.get(i));
        }
    }

    public void printCards(){
        for (Integer i: cardsValue) {
            System.out.println(i);
        }
    }


}
