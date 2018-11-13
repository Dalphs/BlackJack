package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //We set the name of our window to "Black Jack" and set the size of the scene to 1200x600
        //Then we show the scene that contains our pane
        BlackJack game = new BlackJack();
        game.startGame();
        Pane pane = game.getPane();
        primaryStage.setTitle("Black Jack");
        Scene scene = new Scene(pane, 1200, 600);
        scene.getStylesheets().add("sample/Layout.css");
        primaryStage.setScene(scene);
        primaryStage.show();

        ArrayList<Integer> cards = new ArrayList<>();
        ArrayList<Integer> cardsValue = new ArrayList<>();

        cardsValue.add(1);
        cardsValue.add(5);
        cardsValue.add(3);


        Player p = new Player(100, 100 ,100);
        p.addCard(34, 1);
        p.addCard(34, 6);
        System.out.println(p.getSum());
        p.addCard(34, 5);
        System.out.println(p.getSum());
    }


    public static void main(String[] args) {
        launch(args);
    }


}


