package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;

import static java.awt.Color.black;

public class Graphics extends Logic {
    //Pane that we add all out nodes to
    protected static Pane pane = new Pane();
    //4 doubles to control where new cards will get placed in the pane
    public static double dealerX;
    public static double dealerY;
    private static double playerX;
    private static double playerY;
    //Arraylist for storing card imv
    ArrayList<ImageView> cardsImv = new ArrayList<ImageView>();
    //Buttons we use to start the first game, and start a new game
    Button start;
    Button newGame = makeButton("New game", 200,  250, 200, 100);


    public Graphics() {
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
        ImageView player1 = getCard(playerX += 50, playerY );
        ImageView dealer1 = getCard(dealerX += 50, dealerY );
        ImageView player2 = getCard(playerX += 50, playerY );
        ImageView dealer2 = getCard(dealerX += 50, dealerY );

        //We add the four cards to the pane
        pane.getChildren().addAll(player1, dealer1, player2, dealer2);
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
        if(cardIndex != 3) {
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
            //We declare a new ImageView newCard and assign the value of an imageview created in the getCard method
            //We set the x and y coordinate to the class variables playerX and PlayerY and increases the playerX
            //variable by 50, so the next card will be placed further to the right
            ImageView newCard = getCard(playerX += 50, playerY);
            //We add the new Imageview to the pane
            pane.getChildren().add(newCard);
            lastCard++;
            if(playerBusted()) {
                pane.getChildren().add(addText("You busted!!!"));
                pane.getChildren().add(newGame);
            }
        }
    }

    //This class handles a certain event
    class StandHandler implements EventHandler<ActionEvent> {
        //This method displays the dealers card face-up
        @Override
        public void handle(ActionEvent e){
            //We create a new image, and we know its card number 3, since that the card the dealer has face down
            Image card = new Image("card/" + cards.get(3) + ".png");
            //We create an imageView to display the Image of the card and set the width and height of it
            ImageView cardImv = new ImageView(card);
            cardImv.setFitWidth(80);
            cardImv.setFitHeight(120);
            //We set the coordinates as the class variables dealerX and dealerY.
            // We don't increase dealerX this time, since we want the card on top of the face down card
            cardImv.setLayoutX(dealerX);
            cardImv.setLayoutY(dealerY);
            //We add the ImageView to the pane
            pane.getChildren().add(cardImv);
            dealerSum();
            //We set the class variable lastCard to the current cardIndex.
            //We know the player doesnt want anyymore cards because the player has pressed stand
            lastCard = cardIndex;
            //This loop adds the dealers cards
            for (int i = 0; i < dealerNumberOfHits; i++) {
                pane.getChildren().add(getCard(dealerX += 50, dealerY));
            }
            Text dealerCount = addText(Integer.toString(dealerSum), 460, 100);
            pane.getChildren().add(dealerCount);
            //If the dealer busted, it will add text that says the dealer busted and a button to start a new game
            if(dealerBusted()) {
                pane.getChildren().add(addText("Dealer busted"));
                pane.getChildren().add(newGame);
            }
            //This gets the winner using the getWinner method and creates a button to start a new game
            else {
                getWinner();
                pane.getChildren().add(newGame);
            }
        }
    }

    //This method tells the user who won
    public void getWinner(){
        //We know neither the dealer nor player busted so whoever has the highest score wins, and if they're even they draw
        System.out.println("playerSum: " + playerSum + " dealerSum: " + dealerSum);
        if(dealerSum < playerSum)
            pane.getChildren().add(addText("You won!!"));
        if(dealerSum > playerSum)
            pane.getChildren().add(addText("You lost!!!"));
        if(dealerSum == playerSum)
            pane.getChildren().add(addText("Its a draw"));
    }

    //This method takes a string and returns it as a Text object
    public Text addText(String title){
        //We create a new text from the string and sets its position and font
        Text text = new Text(title);
        text.setX(400); text.setY(300);
        text.setId("winning-text");
        return text;
    }

    //This method takes a string and returns it as a Text object
    public Text addText(String title, double x, double y){
        //We create a new text from the string and sets its position and font
        Text text = new Text(title);
        text.setX(x); text.setY(y);
        text.setId("points-text");
        return text;
    }

    //classs StartHandler for handling certain events
    class StartHandler implements EventHandler<ActionEvent> {
        //This method starts a new game
        @Override
        public void handle(ActionEvent e){
            //First we set/reset our classvariables, so all methods works as intended
            dealerX = 440;
            dealerY = 65;
            playerX = 440;
            playerY = 350;
            dealerNumberOfHits = 0;
            dealerSum = 0;
            playerSum = 0;
            lastCard = 3;
            cardIndex = 0;
            //We clear the arraylists with cards and cardsValues as well as the pane that displays out game
            cardsValue.clear();
            pane.getChildren().clear();
            cards.clear();
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
            playerCount = addText(Integer.toString(playerSum), 460, 440);
            pane.getChildren().add(playerCount);
            playerSum();
        }
    }

    public Pane getPane() {
        return pane;
    }
}
