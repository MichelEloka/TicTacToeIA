package test.frontfx;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class TicTacToe2 extends Application {
    private boolean isPlayer1Turn = true;
    private boolean gameOver = false;
    private String[][] board = new String[3][3]; 
    private PriorityQueue<Move> moveQueue;
    private  GridPane gridPane = new GridPane();
    private boolean emptyCellsLeft = true;
    private int  drawChecker=0;
    private EndGamePopup endGamePopup= new EndGamePopup();


    


    @Override
    public void start(Stage primaryStage) {
        
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.getStyleClass().add("gridPane");

       
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button button = createButton(i, j);
                gridPane.add(button, j, i);
            }
        }
        
        Button button = new Button("Help");
        button.getStyleClass().add("HelpButton");
        button.setOnAction(e -> {
           
            playComputerTurn();
        });
        
        VBox root = new VBox(gridPane, button);
        root.setSpacing(10);
        root.getStyleClass().add("VBox");
        Scene scene = new Scene(root, 400, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        // scene.getStylesheets().add(getClass().getResource("Style.css").toString());

        scene.setFill(Color.web("#FF000159"));
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setFullScreen(true);

        primaryStage.show();
        
    }



    

    public TicTacToe2() {
        moveQueue = new PriorityQueue<>(Comparator.comparingInt(Move::getScore).reversed());
        // PriorityQueue<Move> moveQueue = new PriorityQueue<>((move1, move2) -> Integer.compare(move2.getScore(), move1.getScore()));

    }




private Button createButton(int row, int col) {
    Button button = new Button();
    button.setPrefSize(120, 120);

    // Dans la méthode createButton(row, col)
button.setOnAction(e -> {
    drawChecker++;
    button.setStyle(" -fx-background-color: #19125466;");
    if (!gameOver && board[row][col] == null) {
        if (isPlayer1Turn) {
            drawCross(button);
            board[row][col] = "X";
        } 

        if (checkWin(row, col)) {
            endGamePopup.display("Juego terminado! Jugador " + (isPlayer1Turn ? "Segundo" : "Primero") + " Gana");
            System.out.println("Jugador " + (isPlayer1Turn ? "Segundo" : "Primero") + " Gana!");
            gameOver = true;
        } else if (drawChecker == 9) {
            gameOver = true;
            endGamePopup.display("Juego terminado! Nulo !");
            System.out.println("Draw!");
        } else {
            // switchTurn(); 
            playComputerTurn2();
            printBoard();
        }
    }
});

    return button;
}


    
    private void drawCross(Button button) {
        Line line1 = new Line(10, 10, 90, 90);
        Line line2 = new Line(10, 90, 90, 10);
        // line1.setStroke(Color.web("#FF17EAF7")); 
        // line2.setStroke(Color.web("#FF17EAF7")); 
        line1.getStyleClass().add("line");
        line2.getStyleClass().add("line");

        button.setGraphic(new javafx.scene.Group(line1, line2));
    }

    
    private void drawCircle(Button button) {
        Circle circle = new Circle(50, 50, 40);
        circle.setStroke(Color.BLACK);
        circle.setFill(Color.TRANSPARENT);
        // circle.setStroke(Color.web("#FF17EAF7")); 
        circle.setFill(Color.TRANSPARENT); 
        circle.getStyleClass().add("Circlee");
        button.setGraphic(circle);
        System.err.println(circle.getClass().getName());
    }

   
    private boolean checkWin(int row, int col) {
        String player = board[row][col];
       
        if (board[row][0] == player && board[row][1] == player && board[row][2] == player)
            return true;
        
        if (board[0][col] == player && board[1][col] == player && board[2][col] == player)
            return true;
        
        if (row == col && board[0][0] == player && board[1][1] == player && board[2][2] == player)
            return true;
        if (row + col == 2 && board[0][2] == player && board[1][1] == player && board[2][0] == player)
            return true;
        return false;
    }


    private void isDraw(){
        
        if(drawChecker==9){
            gameOver=true;

            endGamePopup.display("Juego terminado!  Nulo !");
            System.out.println("Partie Null");

        }
        
    }

   
    private void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String symbol = (board[i][j] == null) ? " " : board[i][j];
                System.out.print(symbol + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int evaluateScoreForMove(int row, int col, String playerSymbol) {
        
    
        int score = 0;
    
        
        score += countAdjacentCells(row, col, 0, 1, playerSymbol) + countAdjacentCells(row, col, 0, -1, playerSymbol);
       
        score += countAdjacentCells(row, col, 1, 0, playerSymbol) + countAdjacentCells(row, col, -1, 0, playerSymbol);
        
        score += countAdjacentCells(row, col, 1, 1, playerSymbol) + countAdjacentCells(row, col, -1, -1, playerSymbol);
        score += countAdjacentCells(row, col, 1, -1, playerSymbol) + countAdjacentCells(row, col, -1, 1, playerSymbol);
       
        score += evaluateThreeInRow(row, col, playerSymbol);
        score += blockOpponentWin(row, col, playerSymbol); 
        if (row == 1 && col == 1 && board[row][col] == null) {
            score += 4; 
        }
    
        return score;
    }

    
    private int blockOpponentWin(int row, int col, String playerSymbol) {
       
        String opponentSymbol = (playerSymbol.equals("X")) ? "O" : "X";
    
        int count = 2;
    
        
        count += (countAdjacentCells(row, col, 0, 1, opponentSymbol) + countAdjacentCells(row, col, 0, -1, opponentSymbol) == 2) ? 5 : 0;
        
        count += (countAdjacentCells(row, col, 1, 0, opponentSymbol) + countAdjacentCells(row, col, -1, 0, opponentSymbol) == 2) ? 5 : 0;
        
        count += (countAdjacentCells(row, col, 1, 1, opponentSymbol) + countAdjacentCells(row, col, -1, -1, opponentSymbol) == 2) ? 5 : 0;
        count += (countAdjacentCells(row, col, 1, -1, opponentSymbol) + countAdjacentCells(row, col, -1, 1, opponentSymbol) == 2) ? 5 : 0;
    
        return count;
    }

private int evaluateThreeInRow(int row, int col, String playerSymbol) {
    int count = 0;

    
    count += (countAdjacentCells(row, col, 0, 1, playerSymbol) + countAdjacentCells(row, col, 0, -1, playerSymbol) == 2) ? 1 : 0;
    
    count += (countAdjacentCells(row, col, 1, 0, playerSymbol) + countAdjacentCells(row, col, -1, 0, playerSymbol) == 2) ? 1 : 0;
    
    count += (countAdjacentCells(row, col, 1, 1, playerSymbol) + countAdjacentCells(row, col, -1, -1, playerSymbol) == 2) ? 1 : 0;
    count += (countAdjacentCells(row, col, 1, -1, playerSymbol) + countAdjacentCells(row, col, -1, 1, playerSymbol) == 2) ? 1 : 0;

    return count;
}
    
    private int countAdjacentCells(int row, int col, int dx, int dy, String playerSymbol) {
        int count = 0;
        row += dx;
        col += dy;
        while (isValidPosition(row, col) && board[row][col] != null && board[row][col].equals(playerSymbol)) {
            count++;
            row += dx;
            col += dy;
        }
        return count;
    }

    
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3;
    }
    
    private void evaluateBestMoves(String playerSymbol) {
        
        moveQueue.clear();
        List<Move> moves = new ArrayList<>();
    
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == null) {
                    int score = evaluateScoreForMove(i, j, playerSymbol);
                    moves.add(new Move(i, j, score));
                }
            }
        }
        
        Collections.shuffle(moves);

        
        for (Move move : moves) {
            moveQueue.offer(move);
        }
    }

    private Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
    Node result = null;
    ObservableList<Node> children = gridPane.getChildren();

    for (Node node : children) {
        if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
            result = node;
            break;
        }
    }

    return result;
}


private void playComputerTurn() {
    if (!gameOver) {
        

        if (isPlayer1Turn) {
            evaluateBestMoves("X");
        }else{
            evaluateBestMoves("O");
        }

         
        if (!moveQueue.isEmpty()) {
            Move bestMove = moveQueue.poll(); 
            int row = bestMove.getRow();
            int col = bestMove.getCol();
            Button button = (Button) getNodeByRowColumnIndex(row, col, gridPane);
            button.setStyle("-fx-background-color: lightblue;");


            
            // button.setDisable(true);
            // board[row][col] = isPlayer1Turn ? "X" : "O";
            // if (checkWin(row, col)) {
            //     System.out.println("Player " + (isPlayer1Turn ? "1" : "2") + " wins!");
            //     gameOver = true;
            // }
            // isPlayer1Turn = true;
            // computerTurnAllowed = false; // Bloquer l'ordinateur de jouer jusqu'à ce que le joueur joue
        }
    }
}
private void playComputerTurn2() {
    if (!gameOver) {
        
        String playerSymbol = isPlayer1Turn ? "X" : "O";
        evaluateBestMoves(playerSymbol);
        

        
        if (!moveQueue.isEmpty()) {
            Move bestMove = moveQueue.poll();
            int row = bestMove.getRow();
            int col = bestMove.getCol();
            Button button = (Button) getNodeByRowColumnIndex(row, col, gridPane);
            
            drawCircle(button);
            board[row][col] = "O";
            drawChecker++;
            if (checkWin(row, col)) {
                endGamePopup.display("Juego terminado! \n tú pierdes!");
                System.out.println("Juego terminado! \n Jugador  tú pierdes!");
                gameOver = true;
            } 
            // button.setDisable(true);
            // board[row][col] = playerSymbol;
            // if (checkWin(row, col)) {
            //     endGamePopup.display("Juego terminado! Player " + (isPlayer1Turn ? "2" : "1") + " wins!");
            //     System.out.println("Player " + (isPlayer1Turn ? "2" : "1") + " wins!");
            //     gameOver = true;
            // }
            // switchTurn(); // Passer au tour suivant
        }
    }
}

    public static void main(String[] args) {
        launch(args);
    }



}
