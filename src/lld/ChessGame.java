package lld;




class Game {
    private Board board;
    private Player white;
    private Player black;
    private Player currentTurn;

    public Game(Player white, Player black) {
        this.board = new Board();
        this.white = white;
        this.black = black;
        this.currentTurn = white; // White starts
        initializePieces();
    }


    private void initializePieces() {
        // White pieces
        board.placePiece(new Rook(Color.WHITE, new Position(0,0)), new Position(0,0));
        board.placePiece(new Knight(Color.WHITE, new Position(0,1)), new Position(0,1));
        board.placePiece(new Bishop(Color.WHITE, new Position(0,2)), new Position(0,2));
        board.placePiece(new Queen(Color.WHITE, new Position(0,3)), new Position(0,3));
        board.placePiece(new King(Color.WHITE, new Position(0,4)), new Position(0,4));
        board.placePiece(new Bishop(Color.WHITE, new Position(0,5)), new Position(0,5));
        board.placePiece(new Knight(Color.WHITE, new Position(0,6)), new Position(0,6));
        board.placePiece(new Rook(Color.WHITE, new Position(0,7)), new Position(0,7));

        for (int i = 0; i < 8; i++) {
            board.placePiece(new Pawn(Color.WHITE, new Position(1,i)), new Position(1,i));
        }

        // Black pieces
        board.placePiece(new Rook(Color.BLACK, new Position(7,0)), new Position(7,0));
        board.placePiece(new Knight(Color.BLACK, new Position(7,1)), new Position(7,1));
        board.placePiece(new Bishop(Color.BLACK, new Position(7,2)), new Position(7,2));
        board.placePiece(new Queen(Color.BLACK, new Position(7,3)), new Position(7,3));
        board.placePiece(new King(Color.BLACK, new Position(7,4)), new Position(7,4));
        board.placePiece(new Bishop(Color.BLACK, new Position(7,5)), new Position(7,5));
        board.placePiece(new Knight(Color.BLACK, new Position(7,6)), new Position(7,6));
        board.placePiece(new Rook(Color.BLACK, new Position(7,7)), new Position(7,7));

        for (int i = 0; i < 8; i++) {
            board.placePiece(new Pawn(Color.BLACK, new Position(6,i)), new Position(6,i));
        }
    }

    public void move(Position from, Position to) {
        Piece piece = board.getPiece(from);
        if (piece == null) {
            System.out.println("No piece at source.");
            return;
        }
        if (piece.getColor() != currentTurn.getColor()) {
            System.out.println("Not your turn!");
            return;
        }
        if (!piece.isValidMove(from, board)) {
            System.out.println("Invalid move!");
            return;
        }

        board.movePiece(from, to);
        currentTurn = (currentTurn == white) ? black : white;
        checkGameStatus();
    }

    public void checkGameStatus() {
        Color currentColor = currentTurn.getColor();

        if (isInCheck(currentColor) && !hasAnyValidMove(currentColor)) {
            System.out.println(currentTurn.getName() + " is checkmated!");
            Player winner = (currentTurn == white) ? black : white;
            System.out.println("🏆 Winner: " + winner.getName());
        }
        else if (!isInCheck(currentColor) && !hasAnyValidMove(currentColor)) {
            System.out.println("Game is a draw (stalemate).");
        }
    }


    private boolean isInCheck(Color color) {
        Position kingPos = findKing(color);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece attacker = board.getPiece(new Position(i, j));
                if (attacker != null && attacker.getColor() != color) {
                    if (attacker.isValidMove(kingPos, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Position findKing(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(i, j));
                if (piece instanceof King && piece.getColor() == color) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }


    private boolean hasAnyValidMove(Color color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(i, j));
                if (piece != null && piece.getColor() == color) {
                    // Try all 64 destination squares
                    for (int x = 0; x < 8; x++) {
                        for (int y = 0; y < 8; y++) {
                            Position from = new Position(i, j);
                            Position to = new Position(x, y);

                            if (piece.isValidMove(to, board)) {

                                // some logic
                            }
                        }
                    }
                }
            }
        }
        return false; // no valid moves
    }

}


class Player {
    private String name;
    private Color color;
    private boolean isHuman;

    public Player(String name, Color color, boolean isHuman) {
        this.name = name;
        this.color = color;
        this.isHuman = isHuman;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public boolean isHuman() {
        return isHuman;
    }
}



enum Color { WHITE, BLACK; }

class Position {
    private int x; // row
    private int y; // column

    public Position(int x, int y) {
        this.x = x; this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
}

class Board {
    private Piece[][] grid = new Piece[8][8];

    public void placePiece(Piece piece, Position pos) {
        grid[pos.getX()][pos.getY()] = piece;
    }

    public Piece getPiece(Position pos) {
        return grid[pos.getX()][pos.getY()];
    }

    public void movePiece(Position from, Position to) {
        Piece piece = getPiece(from);
        if (piece != null && piece.isValidMove(to, this)) {
            grid[to.getX()][to.getY()] = piece;
            grid[from.getX()][from.getY()] = null;
            piece.setPosition(to);
        }
    }
}



abstract class Piece {
    protected Color color;
    protected Position position;

    public Piece(Color color, Position position) {
        this.color = color;
        this.position = position;
    }

    public Color getColor() { return color; }
    public Position getPosition() { return position; }
    public void setPosition(Position position) { this.position = position; }

    abstract boolean isValidMove(Position newPosition, Board board);
}


class King extends Piece {
    public King(Color color, Position position) { super(color, position); }

    @Override
    boolean isValidMove(Position newPos, Board board) {
        int dx = Math.abs(newPos.getX() - position.getX());
        int dy = Math.abs(newPos.getY() - position.getY());
        return dx <= 1 && dy <= 1; // one square in any direction
    }
}

class Queen extends Piece {
    public Queen(Color color, Position position) { super(color, position); }

    @Override
    boolean isValidMove(Position newPos, Board board) {
        int dx = Math.abs(newPos.getX() - position.getX());
        int dy = Math.abs(newPos.getY() - position.getY());
        return dx == dy || dx == 0 || dy == 0; // diagonal, vertical, horizontal
    }
}

class Rook extends Piece {
    public Rook(Color color, Position position) { super(color, position); }

    @Override
    boolean isValidMove(Position newPos, Board board) {
        return newPos.getX() == position.getX() || newPos.getY() == position.getY();
    }
}

class Bishop extends Piece {
    public Bishop(Color color, Position position) { super(color, position); }

    @Override
    boolean isValidMove(Position newPos, Board board) {
        int dx = Math.abs(newPos.getX() - position.getX());
        int dy = Math.abs(newPos.getY() - position.getY());
        return dx == dy;
    }
}

class Knight extends Piece {
    public Knight(Color color, Position position) { super(color, position); }

    @Override
    boolean isValidMove(Position newPos, Board board) {
        int dx = Math.abs(newPos.getX() - position.getX());
        int dy = Math.abs(newPos.getY() - position.getY());
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}

class Pawn extends Piece {
    public Pawn(Color color, Position position) { super(color, position); }

    @Override
    boolean isValidMove(Position newPos, Board board) {
        int dx = newPos.getX() - position.getX();
        int dy = Math.abs(newPos.getY() - position.getY());

        // White pawns move "up" (x+1), black pawns move "down" (x-1)
        if (color == Color.WHITE) {
            return dx == 1 && dy == 0; // basic forward move
        } else {
            return dx == -1 && dy == 0;
        }
    }
}

