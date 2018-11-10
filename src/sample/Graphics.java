package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static java.awt.Color.black;

public class Graphics extends Logic {
    //Pane that we add all out nodes to
    private static Pane pane = new Pane();
    //4 doubles to control where new cards will get placed in the pane
    public static double dealerX = 440;
    public static double dealerY = 65;
    private static double playerX = 440;
    private static double playerY = 350;
    //Arraylist for storing card imv
    ArrayList<ImageView> cardsImv = new ArrayList<ImageView>();


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

    public void dealCards(){
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
            lastCard++;
            if(playerBusted())
                pane.getChildren().add(addText("You busted!!!"));
            System.out.println(playerSum);
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
            //We set the class variable lastCard to the current cardIndex.
            //We know the player doesnt want anyymore cards because the player has pressed stand
            lastCard = cardIndex;
            dealerSum = dealerSum();
            for (int i = 0; i < dealerNumberOfHits; i++) {
                pane.getChildren().add(getCard(dealerX += 50, dealerY));
            }
            System.out.println("dealerbusted: " + dealerBusted());
            if(dealerBusted())
                pane.getChildren().add(addText("Dealer busted\n You won!!"));
            else
                getWinner();
        }
    }

    public void getWinner(){
        playerSum();
        System.out.println("Playersum: " + playerSum);
        System.out.println("DealerSum: " + dealerSum);
        if(dealerSum < playerSum)
            pane.getChildren().add(addText("Congratulations\n You won!!"));
        if(dealerSum > playerSum)
            pane.getChildren().add(addText("You lost!!!"));
        if(dealerSum == playerSum)
            pane.getChildren().add(addText("Its a draw"));
    }

    public Text addText(String title){
        Text text = new Text(title);
        text.setX(400); text.setY(300);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 70));
        return text;
    }

    public Pane getPane() {
        return pane;
    }
}
