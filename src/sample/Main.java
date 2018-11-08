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
    private static ArrayList<Integer> cards = new ArrayList<>();
    private static int cardIndex = 0;
    private static Pane pane = new Pane();
    private static double dealerX = 440;
    private static double dealerY = 65;
    private static double playerX = 440;
    private static double playerY = 350;
    private static int lastPlayerCard;
    private static Logic game;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Image table = new Image("/bord.jpg");
        ImageView tableImv = new ImageView(table);
        tableImv.setFitHeight(600);
        tableImv.setFitWidth(1200);
        pane.getChildren().add(tableImv);

        Button hit = makeButton("Hit", 0, 500);
        HitHandler hitHandler = new HitHandler();
        hit.setOnAction(hitHandler);

        Button stand = makeButton("Stand", 1050, 500);
        StandHandler standHandler = new StandHandler();
        stand.setOnAction(standHandler);

        pane.getChildren().addAll(hit, stand);


        fillArray(cards);
        startGame();
        game = new Logic(cards);
        game.getValues();



        primaryStage.setTitle("Black Jack");
        primaryStage.setScene(new Scene(pane, 1200, 600));
        primaryStage.show();

        //while(!Logic.gameFinished()){ }



    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Button makeButton(String name, double x, double y){
        Button button = new Button(name);
        button.setPrefSize(150,100);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setStyle("-fx-background-color: #000000, linear-gradient(#7ebcea, #2f4b8f), " +
                        "linear-gradient(#426ab7, #263e75), linear-gradient(#395cab, #223768); " +
                "-fx-background-insets: 0,1,2,3; " +
                "-fx-background-radius: 3,2,2,2; -fx-padding: 12 30 12 30; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 24px;");
        return button;
    }

    public static void fillArray(ArrayList<Integer> list){
        for (int i = 1; i <= 52; i++) {
            list.add(i);
        }
    }

    public static void startGame(){
        cardIndex = 0;
        Collections.shuffle(cards);

        ImageView player1 = getCard(playerX += 50, playerY );
        ImageView dealer1 = getCard(dealerX += 50, dealerY );
        ImageView player2 = getCard(playerX += 50, playerY );
        ImageView dealer2 = getCard(dealerX += 50, dealerY );

        pane.getChildren().addAll(player1, dealer1, player2, dealer2);
    }

    public static ImageView getCard(double x, double y){
       Image card;
        if(cardIndex != 3) {
            card = new Image("card/" + cards.get(cardIndex) + ".png");
        } else {
            card = new Image("card/b1fv.png");
        }
        ImageView cardImv = new ImageView(card);
        cardImv.setFitWidth(80);
        cardImv.setFitHeight(120);
        cardImv.setLayoutX(x);
        cardImv.setLayoutY(y);
        cardIndex++;
        return cardImv;
    }

    class HitHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e){
            ImageView newCard = getCard(playerX += 50, playerY);
            pane.getChildren().add(newCard);
        }
    }

    class StandHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e){
            Image card = new Image("card/" + cards.get(3) + ".png");
            ImageView cardImv = new ImageView(card);
            cardImv.setFitWidth(80);
            cardImv.setFitHeight(120);
            cardImv.setLayoutX(dealerX);
            cardImv.setLayoutY(dealerY);
            pane.getChildren().add(cardImv);
            lastPlayerCard = cardIndex;
        }
    }

    public static ArrayList<Integer> getCards() {
        return cards;
    }

    public static int getLastPlayerCard() {
        return lastPlayerCard;
    }
}


