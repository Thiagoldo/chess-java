package application;

import boardgame.Piece;
import chess.ChessPiece;

public class UI {
    public static void printPieces(ChessPiece[][] pieces){
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((pieces.length - i) + " ");
            for (int j = 0; j < pieces.length; j++) {
                printPiece(pieces[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    private static void printPiece(ChessPiece chessPiece){
        if (chessPiece == null){
            System.out.print("-");
        } else {
            System.out.print(chessPiece);
        }
        System.out.print(" ");
    }
}
