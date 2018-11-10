package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //We set the name of our window to "Black Jack" and set the size of the scene to 1200x600
        //Then we show the scene that containns our pane*/
        BlackJack game = new BlackJack();
        game.startGame();
        Pane pane = game.getPane();
        primaryStage.setTitle("Black Jack");
        primaryStage.setScene(new Scene(pane, 1200, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


}


