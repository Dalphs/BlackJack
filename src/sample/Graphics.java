package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;


public class Graphics extends Logic {
    GridPane bettingPane;
    //Pane that we add all out nodes to
    protected static Pane pane = new Pane();
    //Arraylist for storing card imv
    ArrayList<ImageView> cardsImv = new ArrayList<ImageView>();
    ArrayList<Text> counters = new ArrayList<>();
    //Buttons we use to start the first game, and start a new game
    Button start;
    Button newGame = makeButton("New game", 200,  250, 200, 100);
    private Circle playerMarker;
    private int betTurn = 0;
    private TextField bet;
    Text placeYourBets;


    public Graphics() {
    }

    public void createPlayerMarker(){
        playerMarker = new Circle(players.get(0).getX() - 30, players.get(0).getY() + 60, 25, Color.CYAN);
        playerMarker.setOpacity(0.5);
        pane.getChildren().add(playerMarker);
    }

    public void rearrangeCircle(Actors a){
        playerMarker.setCenterX(a.getX() - 30);
        playerMarker.setCenterY(a.getY() + 60);
    }

    public void defaultLayout(){
        //First we add an image from our src folder int the current project
        // The we create and ImageView to show the picture, and sets the height and width of the picture,
        // to fit our scene. Then we add it to the pane
        Image table = new Image("/bord.jpg");
        ImageView tableImv = new ImageView(table);
        tableImv.setFitHeight(600);
        tableImv.setFitWidth(1200);
        pane.getChildren().add(tableImv);

        //We use the button method to create a button with a specified text and placement
        //Then we create a hitHandler to handle when a button is clicked, and connects the button to the handler
        Button hit = makeButton("Hit", 0, 500, 150, 100);
        HitHandler hitHandler = new HitHandler();
        hit.setOnAction(hitHandler);

        //We create a new button, the same way as above(This should probably be a method,
        // since its basically the same code as above
        Button stand = makeButton("Stand", 1050, 500, 150, 100);
        StandHandler standHandler = new StandHandler();
        stand.setOnAction(standHandler);

        //We add both buttons to our pane
        pane.getChildren().addAll(hit, stand);
    }

    //This button makes a new button with a specified text, location and size in a pane
    public static Button makeButton(String name, double x, double y, int width, int height){
        //We make an instance of the Button class and sets its size to 150x100
        Button button = new Button(name);
        button.setPrefSize(width,height);
        //We set the placement of our button in a pane with the specified x and y coordinates
        button.setLayoutX(x);
        button.setLayoutY(y);

        //We return the new button we have just created
        return button;
    }

    //This method deals the first 2 cards to the player and dealer
    public void dealCards(){
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j <= numberOfPlayers; j++) {
                ImageView temp;
                if (j == numberOfPlayers){
                    temp = getCard(dealer.getX(), dealer.getY());
                    dealer.setX(dealer.getX() + 50);
                }else {
                    temp = getCard(players.get(j).getX(), players.get(j).getY());
                    players.get(j).setX(players.get(j).getX() + 50);
                }
                pane.getChildren().add(temp);
                cardsImv.add(temp);
                System.out.println("Card number " + cardIndex + " is: " + cardsValue.get(cardIndex - 1));
            }
        }
    }

    //This method creates a startbutton, that will start our first game
    public void startButton(){
        //We assign the classvariable Button "start" the value of a new button
        start = makeButton("Start game", 500, 250, 200, 100);
        //We create a new StartHandler object to take action when start is pressed
        StartHandler startHandler = new StartHandler();
        start.setOnAction(startHandler);
        newGame.setOnAction(startHandler);
        //We add the new button to our pane
        pane.getChildren().add(start);
    }

    //Method that creates the corresponding imageView for the next value in the cards array
    //The methods receives varibles for the coordinates of the Imageviews position in the pane
    public static ImageView getCard(double x, double y){
        //First we declare an instance of the image class as card
        Image card;
        //If its the fourth card we want it to face down, all others card should be displayed face up
        if(cardIndex != numberOfPlayers * 2 + 1) {
            //Since each picture has the same file name except for the number
            //and are in the same folder, we only need to change the number
            card = new Image("card/" + cards.get(cardIndex) + ".png");
        } else {
            card = new Image("card/b1fv.png");
        }
        //We create an imageview for our card image and sets the width and height of it
        ImageView cardImv = new ImageView(card);
        cardImv.setFitWidth(80);
        cardImv.setFitHeight(120);
        //We set the coordinates of the cards to the specified coordinates
        cardImv.setLayoutX(x);
        cardImv.setLayoutY(y);
        //Incrementing cardIndex, since we want the next card, next time
        cardIndex++;
        // We return the imageView of the card we created
        return cardImv;
    }

    //classs HitHandler for handling certain events
    class HitHandler implements EventHandler<ActionEvent> {
        //This method is creates a new card when the player presses the hit button
        @Override
        public void handle(ActionEvent e){
            printCards();
            updateCounter();
            if(isBusted(players.get(playerTurn).getSum())){
                players.get(playerTurn).setBusted(true);
                pane.getChildren().add(addBustedText(players.get(playerTurn).getX() - 150, players.get(playerTurn).getY()));
                reward(2, players.get(playerTurn));
                playerTurn++;
                rearrangeCircle(players.get(playerTurn));
            }
            if (playerTurn == numberOfPlayers && !allPlayersBusted()){
                finishDealer();
            }else if (allPlayersBusted()){
                pane.getChildren().remove(playerMarker);
            }
        }
    }

    public void finishDealer(){
        showDealerCard();
        while(dealer.getSum()< 17) {
            printCards();
        }
        System.out.println("Dealer busted: " + dealer.isBusted());
        if(!dealer.isBusted()) {
            for (int i = 0; i < numberOfPlayers; i++) {
                if (!players.get(i).isBusted()) {
                    getWinner(players.get(i));
                }
            }
        } else{
            for (int i = 0; i < numberOfPlayers; i++) {
                if (!players.get(i).isBusted()) {
                    addWonText(players.get(i).getX(), players.get(i).getY());
                    reward(1, players.get(i));
                }
            }

        }
        for (Player p:players) {
            System.out.println("Player money: " + p.getMoney());
        }
    }

    public void bettingRound(){
        bet = new TextField();
        placeYourBets = new Text("Place your bets");
        placeYourBets.setId("betting-text");
        placeYourBets.setX(350);placeYourBets.setY(300);
        Button betButton = makeButton("Bet", 20, 20, 80,30);
        betButton.setId("bet-button");
        BetHandler betHandler = new BetHandler();
        betButton.setOnAction(betHandler);
        bettingPane = new GridPane();
        bettingPane.setLayoutX(players.get(0).getX() + 30);
        bettingPane.setLayoutY(players.get(0).getY() + 50);


        bettingPane.add(bet, 0, 0);
        bettingPane.add(betButton, 0, 1);
        pane.getChildren().add(bettingPane);
        pane.getChildren().add(placeYourBets);

    }

    public void updateCounter(){
        counters.get(playerTurn).setText(Integer.toString(players.get(playerTurn).getSum()));
    }

    public void printCards(){
        ImageView newCard;
        if(playerTurn  < numberOfPlayers) {
            System.out.println(" sum : " + players.get(playerTurn).getSum());
            players.get(playerTurn).addCard(cards.get(cardIndex), cardsValue.get(cardIndex));
            System.out.println("Hitcard value: " + cardsValue.get(cardIndex));
            System.out.println("New sum : " + players.get(playerTurn).getSum());
            //We declare a new ImageView newCard and assign the value of an imageview created in the getCard method
            //We set the x and y coordinate to the class variables playerX and PlayerY and increases the playerX
            //variable by 50, so the next card will be placed further to the right
            newCard = getCard(players.get(playerTurn).getX(), players.get(playerTurn).getY());
            players.get(playerTurn).setX(players.get(playerTurn).getX() + 50);
        } else {
            dealer.addCard(cards.get(cardIndex), cardsValue.get(cardIndex));
            newCard = getCard(dealer.getX(), dealer.getY());
            dealer.setX(dealer.getX() + 50);
        }
        //We add the new Imageview to the pane
        pane.getChildren().add(newCard);
    }



    //This class handles a certain event
    class StandHandler implements EventHandler<ActionEvent> {
        //This method displays the dealers card face-up
        @Override
        public void handle(ActionEvent e) {
            playerTurn++;
            if (playerTurn == numberOfPlayers) {
                pane.getChildren().remove(playerMarker);
                finishDealer();

            }else
                rearrangeCircle(players.get(playerTurn));
        }
    }

        public void showDealerCard(){
            //We create a new image, and we know its card number 3, since that the card the dealer has face down
            Image card = new Image("card/" + cards.get(numberOfPlayers * 2 + 1) + ".png");
            //We create an imageView to display the Image of the card and set the width and height of it
            ImageView cardImv = new ImageView(card);
            cardImv.setFitWidth(80);
            cardImv.setFitHeight(120);
            //We set the coordinates as the class variables dealerX and dealerY.
            // We don't increase dealerX this time, since we want the card on top of the face down card
            cardImv.setLayoutX(dealer.getX() - 50);
            cardImv.setLayoutY(dealer.getY());
            cardsImv.add(numberOfPlayers * 2 + 1, cardImv);
            pane.getChildren().remove(cardsImv.get(numberOfPlayers * 2 + 2));
            cardsImv.remove(numberOfPlayers * 2+ 2);
            //We add the ImageView to the pane
            pane.getChildren().add(cardImv);
        }


    //This method tells the user who won
    public void getWinner(Player a){
        //We know neither the dealer nor player busted so whoever has the highest score wins, and if they're even they draw
        if(a.getSum() < dealer.getSum()) {
            addLostText(a.getX() - a.getNumberOfCards() * 50, a.getY());
            reward(2, a);
        }
        if(a.getSum() > dealer.getSum()) {
            addWonText(a.getX() - a.getNumberOfCards() * 50, a.getY());
            reward(1, a);
        }
        if(a.getSum() == dealer.getSum()) {
            addDrawText(a.getX() - a.getNumberOfCards() * 50, a.getY());
            reward(4, a);
        }
    }

    public Text addCounter(String number){
        Text text = new Text(number);
        text.setId("counter-text");
        return text;
    }

    public void createCounters(){
        for (int i = 0; i < numberOfPlayers; i++){
            Text temp = addCounter(Integer.toString(players.get(i).getSum()));
            counters.add(temp);
            temp.setX(players.get(i).getX() - 125);
            temp.setY(players.get(i).getY() + 110);
            pane.getChildren().add(temp);
        }
    }

    //This method takes a string and returns it as a Text object
    public Text addBustedText(double x, double y){
        //We create a new text from the string and sets its position and font
        Text text = new Text("Busted");
        text.setX(x); text.setY(y);
        text.setId("busted-text");
        return text;
    }

    public void addWonText(double x, double y){
        Text text = new Text("Won");
        text.setX(x); text.setY(y);
        text.setId("won-text");
        pane.getChildren().add(text);
    }

    public void addLostText(double x, double y){
        Text text = new Text("Lost");
        text.setX(x); text.setY(y);
        text.setId("busted-text");
        pane.getChildren().add(text);
    }

    public void addDrawText(double x, double y){
        Text text = new Text("Draw");
        text.setX(x); text.setY(y);
        text.setId("won-text");
        pane.getChildren().add(text);
    }

    public void resetGraphics(){
        pane.getChildren().clear();
    }

    //classs StartHandler for handling certain events
    class StartHandler implements EventHandler<ActionEvent> {
        //This method starts a new game
        @Override
        public void handle(ActionEvent e){
            //First we set/reset our classvariables, so all methods works as intended
            resetLogic();
            resetGraphics();
            //Prints the default layout with background and buttons
            defaultLayout();
            //Fills the vleared array, shuffles it, then copies it to the cardValues arraylist
            fillArray(cards);
            Collections.shuffle(cards);
            copyArray();
            //converts the values from the cards arraylist to the corresponding points in blackjack
            getValues();
            //Giives the player and dealer 2 cards each
            dealCards();
            //Removes the start button
            pane.getChildren().remove(start);
            pane.getChildren().add(playerCount);
        }
    }

    class BetHandler implements EventHandler<ActionEvent> {
        //This method starts a new game
        @Override
        public void handle(ActionEvent e){
            double playerBet = Double.parseDouble(bet.getText());
            bet.clear();
            players.get(betTurn).setCurrentBet(playerBet);
            System.out.println("Player " + betTurn + " has bet " + players.get(betTurn).getCurrentBet());
            betTurn++;
            if (betTurn != numberOfPlayers) {
                bettingPane.setLayoutX(players.get(betTurn).getX() + 30);
                bettingPane.setLayoutY(players.get(betTurn).getY() + 50);
            } else {
                pane.getChildren().remove(bettingPane);
                pane.getChildren().remove(placeYourBets);
            }
        }
    }

    public Pane getPane() {
        return pane;
    }
}
