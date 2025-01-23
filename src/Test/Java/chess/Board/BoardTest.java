package chess.Board;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chess.Pieces.Alliance;
import chess.Pieces.King;
import chess.Pieces.Piece;
import chess.Pieces.Queen;
import chess.Pieces.Rook;
import chess.gui.Table;
import chess.Pieces.Knight;



public class BoardTest {
    


    Board board = new Board();

    @Test
    void NoMovesAble(){
        board.createBoard();

        King king = board.getKing(Alliance.White);
        for(int i =56;i<64;i++){
            Piece p = board.getTile(i).getPiece();
            if(p instanceof Knight){continue;}
            Assertions.assertTrue(p.calculateMoves(board).isEmpty());
        }
        Assertions.assertTrue(king.calculateMoves(board).isEmpty());

    }

    @Test
    void testPieceBlockingCheck(){
        for(int i =0;i<64;i++){board.unOccupy(i);}
        King king = new King(12, Alliance.White);
        King kig = new King(22, Alliance.Black);
        Rook r1 = new Rook(13, Alliance.White);
        Queen r2 = new Queen(14, Alliance.Black);


        board.setPiece((Piece)king,12);
        board.setPiece((Piece)kig,36);
        board.setPiece((Piece)r1,13);
        board.setPiece((Piece)r2,14);


        Assertions.assertEquals(1,r1.calculateMoves(board).size());
        Assertions.assertEquals(1,r2.calculateMoves(board).size());




    }
}
