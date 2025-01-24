package chess.Board;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import chess.Pieces.Alliance;
import chess.Pieces.Bishop;
import chess.Pieces.King;
import chess.Pieces.Piece;
import chess.Pieces.Queen;
import chess.Pieces.Rook;
import chess.gui.Table;
import chess.Pieces.Knight;
import chess.Board.BoardUtils;



public class BoardTest {
    

    BoardUtils utils = new BoardUtils();
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
        Rook r1 = new Rook(13, Alliance.White);
        Queen r2 = new Queen(14, Alliance.White);
        Queen r5 = new Queen(15, Alliance.Black);
        King kig = new King(36, Alliance.Black);
        Bishop b = new Bishop(0, Alliance.Black);
        Rook r3 = new Rook(37, Alliance.Black);
        Knight k = new Knight(2, Alliance.White);


        
        board.setPiece((Piece)kig,36);
        board.setPiece((Piece)b,0);



        board.setPiece((Piece)king,12);
        board.setPiece((Piece)r5,15);
        board.setPiece((Piece)k,2);
        
        board.setPiece((Piece)r1,13);
        board.setPiece((Piece)r2,14);
        board.setPiece((Piece)r3,37);

        Assertions.assertEquals(1,utils.getPiecesInBetween(13, 37));
        Assertions.assertEquals(1,utils.getPiecesInBetween(king.getPosition(), r5.getPosition()));
        
        Assertions.assertEquals(1,r2.calculateMoves(board).size());




    }
}
