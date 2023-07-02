package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class King extends ChessPiece {
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString() {
        return "K";
    }

    private boolean canMove(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p == null || p.getColor() != getColor();
    }

    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position auxPosition = new Position(0, 0);

        //Up
        auxPosition.setValues(position.getRow() - 1, position.getColumn());
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Down
        auxPosition.setValues(position.getRow() + 1, position.getColumn());
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Right
        auxPosition.setValues(position.getRow(), position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //Left
        auxPosition.setValues(position.getRow(), position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //NorthWest
        auxPosition.setValues(position.getRow() - 1, position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //NorthEast
        auxPosition.setValues(position.getRow() - 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //SouthWest
        auxPosition.setValues(position.getRow() + 1, position.getColumn() - 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        //SouthEast
        auxPosition.setValues(position.getRow() + 1, position.getColumn() + 1);
        if (getBoard().positionExists(auxPosition) && canMove(auxPosition)){
            matrix[auxPosition.getRow()][auxPosition.getColumn()] = true;
        }

        return matrix;
    }
}
