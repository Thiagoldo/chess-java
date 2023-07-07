package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {
    private final Board board;
    private Integer turn;
    private Color currentPlayer;
    private final List<Piece> capturedPieces;
    private final List<Piece> piecesOnTheBoard;
    private boolean check;

    public boolean getCheck(){
        return check;
    }

    public Integer getTurn() {
        return turn;
    }

    public Color getCurrentPlayer() {
        return currentPlayer;
    }

    public ChessMatch() {
        board = new Board(8, 8);
        currentPlayer = Color.WHITE;
        turn = 1;
        capturedPieces = new ArrayList<>();
        piecesOnTheBoard = new ArrayList<>();
        initialSetup();
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] matrix = new ChessPiece[board.getRows()][board.getColumns()];
        for (int i = 0; i < board.getRows(); i++){
            for (int j = 0; j < board.getColumns(); j++){
                matrix[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return matrix;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }

    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        check = testCheck(opponent(currentPlayer));

        nextTurn();
        return (ChessPiece) capturedPiece;
    }
    private Piece makeMove(Position source, Position target) {
        Piece p = board.removePiece(source);
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if (capturedPiece != null){
            capturedPieces.add(capturedPiece);
            piecesOnTheBoard.remove(capturedPiece);
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        Piece p = board.removePiece(target);
        board.placePiece(p, source);

        if (capturedPiece!= null){
            board.placePiece(capturedPiece, target);
            piecesOnTheBoard.add(capturedPiece);
            capturedPieces.remove(capturedPiece);
        }
    }

    private Color opponent(Color color) {
        return (color == Color.BLACK) ? Color.WHITE : Color.BLACK;
    }

    private ChessPiece king(Color color) {
        List<Piece> pieces = piecesOnTheBoard.stream().
                filter(piece -> ((ChessPiece)piece).getColor() == color).collect(Collectors.toList());
        for (Piece piece : pieces) {
            if (piece instanceof King){
                return (ChessPiece) piece;
            }
        }
        throw new IllegalStateException("There is no " + color + " king in the board");
    }

    private boolean testCheck(Color color){
        ChessPiece king = king(color);
        Position p = king.getChessPosition().toPosition();
        List<Piece> opponentPieces = piecesOnTheBoard.stream()
                .filter(piece -> ((ChessPiece) piece).getColor() == opponent(color)).collect(Collectors.toList());
        for (Piece piece : opponentPieces){
            if (piece.possibleMoves()[p.getRow()][p.getColumn()]){
                return true;
            }
        }
        return false;
    }

    private void validateSourcePosition(Position source) {
        if (!board.thereIsAPiece(source)){
            throw new ChessException("There is no piece on source position");
        }
        if (currentPlayer != ((ChessPiece)board.piece(source)).getColor()){
            throw new ChessException("The chosen piece is not yours.");
        }
        if (!board.piece(source).isThereAnyPossibleMove()){
            throw new ChessException("There is no possible move for the chosen piece");
        }
    }

    private void validateTargetPosition(Position source, Position target) {
        if (!board.piece(source).possibleMove(target)){
            throw new ChessException("The chosen piece can't move to target position");
        }
    }

    private void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    private void initialSetup(){
        placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
    }

    public boolean isCheck() {
        return check;
    }
}
