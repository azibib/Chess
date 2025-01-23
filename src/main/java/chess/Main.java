package chess;






import chess.Board.Board;
import chess.Pieces.Alliance;
import chess.Pieces.Bishop;
import chess.Pieces.King;
import chess.Pieces.Piece;
import chess.Pieces.Queen;
import chess.Pieces.Rook;
import chess.gui.Table;




public class Main{
    public static void main(String[] args) throws InterruptedException {
        
        
        Table t = new Table();
        Board board = new Board();
        board.createBoard();
        for(int i =0;i<64;i++){board.unOccupy(i);}
        King king = new King(12, Alliance.White);
        Rook r1 = new Rook(13, Alliance.White);
        Queen r2 = new Queen(14, Alliance.White);
        King kig = new King(36, Alliance.Black);
        Bishop b = new Bishop(0, Alliance.Black);
        Rook r3 = new Rook(39, Alliance.Black);


        
        board.setPiece((Piece)kig,36);
        board.setPiece((Piece)b,0);



        board.setPiece((Piece)king,12);
        board.setPiece((Piece)r1,13);
        board.setPiece((Piece)r2,14);
        board.setPiece((Piece)r3,39);

        Table table = new Table(board);
        
        

        

        
        

        

 



        
        
       
    
    }

    



}
