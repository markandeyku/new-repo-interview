package lld;


//functional requirements
/**
 * There will be 2 players
 * we can have n*n board
 *  will it be time bounded match
 *  It will be played by human or robot both
 */

// flow
/**
 * Player1 --> Game ---> Board --> Cell --> STATUS
 * */

// What are the entities involved
/**
 *
 * Step 2: Core Entities
 * Entity	Responsibility
 * Player1	Represents a player with symbol (X/O).
 * Board	Maintains state of the grid, validates moves.
 * Game	Controls flow, checks win/draw state.
 * Move	Represents a single move (row, col, player).
 * GameStatus	Enum → IN_PROGRESS, WIN, DRAW.
 *
 * Player1{
 *     id
 *     symbol X or O
 *     isHuman
 * }
 *
 * Symbol{
 *  X,
 *  O
 * }
 *
 * Cell{
 *     int row;
 *     int col;
 *     isOccupied
 * }
 *
 * Game{
 *     Player1 player1;
 *     Player1 player2;
 *     int boardSize;
 *     Cell[][] board;
 *     Player1 currentPlayer
 *     GameStatus status
 *
 *     + playMove(int x,int y)
 *     + boolean placeMove(int x,int y) -> if valid move then place the move and return true else  false
 *     + void switchPlayer() --> switch the player
 *     + check for winner
 *     + check for the all cells occupied
 *     + check for the time limit
 * }
 *
 * STATUS{
 *     WINNER
 *     DRAW
 *     IN_PROGRESS
 * }
 *
 * GameController{
 *     game is initialized
 *     player will be created
 *     then game will start
 * }
 */

public class TicToeGame {

   private Player1 player1;
   private Player1 player2;
   private int boardSize;
   private Cell[][] board;
   private Player1 currentPlayer;
   private GameStatus status;

    // game is initialized
    public TicToeGame(Player1 player1, Player1 player2, int boardSize, Player1 currentPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.boardSize = boardSize;
        this.board = new Cell[boardSize][boardSize];
        this.currentPlayer = currentPlayer;
        this.status = GameStatus.IN_PROGRESS;
    }

    public void playMove(int x, int y){
        // write logic here
        boolean isValidMove = placeMove(x, y);

        boolean isWinner = checkWinner();
        if(isWinner){
            status = GameStatus.WINNER;
            System.out.println("Player1 " + currentPlayer.getName() + " wins");
            System.exit(0);
        }

        boolean isDraw = checkDraw();
        if(isDraw){
            System.out.println("Draw... ");
            status = GameStatus.WINNER;
            System.exit(0);
        }

        switchPlayer();
    }

    private boolean checkDraw() {
        //check all cells occupied
        for(int i = 0; i<boardSize; i++){
            for(int j = 0; j<boardSize; j++){
                if(!board[i][j].isOccupied) return false;
            }
        }
        return true;
    }

    private boolean checkWinner() {
        //check diagonal, anti diagonal, row, column for winner
        return true;
    }

    private boolean placeMove(int x, int y){
        if(x < 0 || x >= boardSize || y < 0 || y >= boardSize || board[x][y].isOccupied){
            return false;
        }
        board[x][y].isOccupied = true;
        board[x][y].player = currentPlayer;

        return true;
    }

    private void switchPlayer() {
        if(currentPlayer.getSymbol().name().equalsIgnoreCase(player1.getSymbol().name())){
            currentPlayer = player2;
        }
        else{
            currentPlayer = player1;
        }
    }

}

enum GameStatus{
    IN_PROGRESS,WINNER, DRAW
}

enum Symbol{
    X,
    O
}

 class Player1{
    String name;
    Symbol symbol;
    boolean isHuman;

     public Player1(String name, Symbol symbol, boolean isHuman) {
         this.name = name;
         this.symbol = symbol;
         this.isHuman = isHuman;
     }

     public String getName() {
         return name;
     }

     public Symbol getSymbol() {
         return symbol;
     }

     public boolean isHuman() {
         return isHuman;
     }
 }

 class Cell{
    int row;
    int col;
    boolean isOccupied;
    Player1 player;

     public int getRow() {
         return row;
     }

     public void setRow(int row) {
         this.row = row;
     }

     public int getCol() {
         return col;
     }

     public void setCol(int col) {
         this.col = col;
     }

     public boolean isOccupied() {
         return isOccupied;
     }

     public void setOccupied(boolean occupied) {
         isOccupied = occupied;
     }

     public Player1 getPlayer() {
         return player;
     }

     public void setPlayer(Player1 player) {
         this.player = player;
     }
 }



public class TicTacToeMain {
    public static void main(String[] args) {
        Player1 p1 = new Player1("Alice", Symbol.X, false);
        Player1 p2 = new Player1("Bob", Symbol.O, false);

        TicToeGame game = new TicToeGame(p1, p2,3,p1);

        game.playMove(0, 0);
        game.playMove(1, 1);
        game.playMove(0, 1);
        game.playMove(2, 2);
        game.playMove(0, 2); // Alice wins
    }
}