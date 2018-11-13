package sample;

import java.util.ArrayList;

public class Actors {
    private ArrayList<Integer> cardsValue = new ArrayList<>();
    private ArrayList<Integer> cards = new ArrayList<>();
    private double x;
    private double y;
    private int sum = 0;
    private int numberOfCards = 0;

    public Actors() {
    }

    public void calculateSum(){
        sum = 0;
        boolean tenPoints = false;
        for (Integer i: cardsValue) {
            sum += i;
            if (i == 1 && !tenPoints && sum <= 11){
                sum += 10;
                tenPoints = true;
            }
            if(sum > 21 && tenPoints){
                sum -= 10;
                tenPoints = false;
            }
        }
    }

    public void addCard(int card, int cardValue){
        this.cards.add(card);
        this.cardsValue.add(cardValue);
        numberOfCards++;
    }

    public ArrayList<Integer> getCardsValue() {
        return cardsValue;
    }

    public void setCardsValue(ArrayList<Integer> cardsValue) {
        this.cardsValue = cardsValue;
    }

    public ArrayList<Integer> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Integer> cards) {
        this.cards = cards;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getSum() {
        calculateSum();
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(int numberOfCards) {
        this.numberOfCards = numberOfCards;
    }
}
