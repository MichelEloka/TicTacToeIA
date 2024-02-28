package test.frontfx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class EndGamePopup {


    static Boolean isTictactoc;
    Font customFont = Font.loadFont(getClass().getResourceAsStream("Choco"+" " +"Taste"+" "+ "Trial.otf"), 30);


    public EndGamePopup(){

    }
    public void display(String message) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        // popupStage.setTitle("Game Over");
        popupStage.setResizable(false);
         popupStage.initStyle(StageStyle.UNDECORATED);
         Label messagE= new Label(message);
         messagE.getStyleClass().add("message");

        
        Button restartButton = new Button("Reanudar");
        restartButton.setOnAction(e -> {

            if(isTictactoc){
                
                TicTacToe ticTacToe = new TicTacToe();
                Stage stage= new Stage();
                ticTacToe.start(stage);
                popupStage.close(); // Fermez la fenêtre pop-up
                // System.exit(0);
            }else{
                 
                TicTacToe2 ticTacToe = new TicTacToe2();
                Stage stage= new Stage();
                ticTacToe.start(stage);
                popupStage.close(); // Fermez la fenêtre pop-up
                // System.exit(0);
            }
        });

       
        Button quitButton = new Button("Salida");
        quitButton.setOnAction(e -> {
           
            System.exit(0); // Cela fermera l'application entière
        });

        Button HomeBout= new Button(" En Recepción");

        HomeBout.setOnAction(e -> {
            popupStage.close();


            Acceuil acceuil = new Acceuil();
            acceuil.start(new Stage());
            
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(messagE, restartButton,HomeBout ,quitButton);
        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        layout.getStyleClass().add("gridPane");
        popupStage.setScene(scene);
        popupStage.setOnCloseRequest(event -> event.consume());
        popupStage.showAndWait();
    }
}
