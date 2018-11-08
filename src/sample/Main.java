package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class Main extends Application {
    //Class variables
    //Arraylist for storing cards
    private static ArrayList<Integer> cards = new ArrayList<>();
    //Int to keep track of the current index for the cards arraylist
    private static int cardIndex = 0;
    //Pane that we add all out nodes to
    private static Pane pane = new Pane();
    //4 doubles to control where new cards will get placed in the pane
    private static double dealerX = 440;
    private static double dealerY = 65;
    private static double playerX = 440;
    private static double playerY = 350;
    //Int that keeps trac of the players last hit
    private static int lastPlayerCard;
    //An instance of our Logic class, that runs the logic behid the game
    private static Logic game;

    @Override
    public void start(Stage primaryStage) throws Exception{

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
        Button hit = makeButton("Hit", 0, 500);
        HitHandler hitHandler = new HitHandler();
        hit.setOnAction(hitHandler);

        //We create a new button, the same way as above(This should probably be a method,
        // since its basically the same code as above
        Button stand = makeButton("Stand", 1050, 500);
        StandHandler standHandler = new StandHandler();
        stand.setOnAction(standHandler);

        //We add both buttons to our pane
        pane.getChildren().addAll(hit, stand);


        //We use the fillArray method on the cards arraylist
        fillArray(cards);
        //We call the startGame method
        startGame();
        //We make a new instance of our Logic class, and gives it our deck of cards
        game = new Logic(cards);
        //This method changes the cards from specific cards to the general values for the cards
        game.getValues();

        //We set the name of our window to "Black Jack" and set the size of the scene to 1200x600
        //Then we show the scene that containns our pane
        primaryStage.setTitle("Black Jack");
        primaryStage.setScene(new Scene(pane, 1200, 600));
        primaryStage.show();

        //This loop will run as long as the game is not over
        //while(!Logic.gameFinished()){ }



    }


    public static void main(String[] args) {
        launch(args);
    }

    //This button makes a new button with a specified text and location in a pane
    public static Button makeButton(String name, double x, double y){
        //We make an instance of the Button class and sets its size to 150x100
        Button button = new Button(name);
        button.setPrefSize(150,100);
        //We set the placement of our button in a pane with the specified x and y coordinates
        button.setLayoutX(x);
        button.setLayoutY(y);
        //This buttonstyle was found on the internet and makes a beautiful blue button
        button.setStyle("-fx-background-color: #000000, linear-gradient(#7ebcea, #2f4b8f), " +
                        "linear-gradient(#426ab7, #263e75), linear-gradient(#395cab, #223768); " +
                "-fx-background-insets: 0,1,2,3; " +
                "-fx-background-radius: 3,2,2,2; -fx-padding: 12 30 12 30; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 24px;");
        //We return the new button we have just created
        return button;
    }

    //This method fills the cards arraylist with the numbers from 1-52,
    // which will represent all cards in a deck of cards
    //1-13 is spades, 14-26 is hearts, 27-39 is diamonds and 40-52 is clubs
    public static void fillArray(ArrayList<Integer> list){
        for (int i = 1; i <= 52; i++) {
            list.add(i);
        }
    }

    //This method gives the player the first and third card, and the dealer the second
    //and fourth card, where the last one is face down
    public static void startGame(){
        //We reset the class class variable so we in the future can run the program contiously,
        // and every time we want to reset the index counter, because we want the first card
        cardIndex = 0;
        //We use the static method shuffle from the Collection class to shuffle our Cards array,
        // so our deck of card is random
        Collections.shuffle(cards);

        //We create 4 imageviews for the first 4 cards of the game. When we create the cards
        //We also increase the playerX variable so the next card will be placed further to the right
        ImageView player1 = getCard(playerX += 50, playerY );
        ImageView dealer1 = getCard(dealerX += 50, dealerY );
        ImageView player2 = getCard(playerX += 50, playerY );
        ImageView dealer2 = getCard(dealerX += 50, dealerY );

        //We add the four cards to the pane
        pane.getChildren().addAll(player1, dealer1, player2, dealer2);
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
            //We set the class variable lastPlayerCard to the current cardIndex.
            //We know the player doesnt want anyymore cards because the player has pressed stand
            lastPlayerCard = cardIndex;
        }
    }

    //Getter for getting the Arraylist cards from outside the class
    public static ArrayList<Integer> getCards() {
        return cards;
    }

    //Getter fro getting the value of the int getlastPlayerCard from outside the class
    public static int getLastPlayerCard() {
        return lastPlayerCard;
    }
}


