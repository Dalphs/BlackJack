package sample;

public class Player extends Actors {
    private double money;
    private double currentBet;

    public Player(double money, double x, double y) {
        this.money = money;
        super.setX(x);
        super.setY(y);

    }


    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getCurrentBet() {
        return currentBet;
    }

    public void setCurrentBet(double currentBet) {
        this.currentBet = currentBet;
    }
}