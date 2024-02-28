package test.frontfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Acceuil extends Application {
    Font customFont = Font.loadFont(getClass().getResourceAsStream("Apocalypse"+" "+ "Grunge.ttf"), 30);
    Font customFont2 = Font.loadFont(getClass().getResourceAsStream("Scratches.otf"), 30);


    @Override
    public void start(Stage primaryStage) {
        // Fenêtre principale
        VBox root = new VBox(20);
        root.getStyleClass().add("gridPane");
        
        Scene scene = new Scene(root, 300, 200);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

        Text title = new Text("Tic Tac Toe");
        Text subTitle = new Text("Por Eloka");
        title.getStyleClass().add("AcText");
        subTitle.getStyleClass().add("AcText2");

        Button multiPlayerButton = new Button("Multijuego");
        Button singlePlayerButton = new Button("Solo");

        multiPlayerButton.setOnAction(e -> {
            // Ouvrir une nouvelle fenêtre pour le mode multijoueur
            openMultiPlayerWindow();
        });

        singlePlayerButton.setOnAction(e -> {
            // Ouvrir une nouvelle fenêtre pour le mode solo
            openSinglePlayerWindow();
        });

        root.getChildren().addAll(title, subTitle, multiPlayerButton, singlePlayerButton);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu principal");
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    private void openMultiPlayerWindow() {
        EndGamePopup.isTictactoc=true;
       TicTacToe ticTacToe = new TicTacToe();
       
       ticTacToe.start(new Stage());
    }

    private void openSinglePlayerWindow() {
        EndGamePopup.isTictactoc=false;
        TicTacToe2 ticTacToe = new TicTacToe2();
       
       ticTacToe.start(new Stage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
