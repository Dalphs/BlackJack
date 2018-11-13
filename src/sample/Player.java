package sample;

public class Player extends Actors {
    private double money;

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

}