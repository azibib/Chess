package chess.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chess.Pieces.Alliance;
import chess.Pieces.King;
import chess.Pieces.Pawn;
import chess.Pieces.Piece;
import chess.Pieces.Queen;
import chess.Pieces.Rook;

public class Move {
       
        private Piece piece;
        private Board board;
        private int current;
        private int newPosition;
        private Piece lostPiece;
        private BoardUtils utils;
       
        
    
        public Move(int current, int newPosition, Piece piece,Board board){
            this.current = current;
            this.newPosition = newPosition;
            this.piece = piece;
            this.board = board;
            lostPiece = null;
            utils = new BoardUtils();
           
    
    
            
        }
        
    
        public Piece execute(){//unoccupy the current space and then 
            
            board.unOccupy(this.current);//this makes the current spot become an empty tile
            if(piece instanceof Rook){
                Rook rook = (Rook)piece;
                rook.setMoved(true);//if the piece is a rook then set the has moved variable to true
            }
            if(piece instanceof King){
                King king = (King)piece;
                king.sethasMoved(true);//if the piece is a king then set the has moved variable to true
            }
            
    
            
            
            
            if(piece instanceof Pawn){
                Pawn pawn = (Pawn)piece;
                pawn.lastMove(board.returnTotlaMoves());//sets the pawns last move to the current boards total amount of moves
                
                int number = utils.getRowLabel(newPosition);//gets the row to check if its touching a top or bottom row
                if(number==1||number==8){//to turn a regular pawn into a  queen
    
                    lostPiece =board.unOccupy(this.newPosition);//take the pawn off
                    board.Occupy(this.newPosition, new Queen(this.newPosition, piece.getAlliance()));//make it a queen
                    
                }else{
                    lostPiece = board.Occupy(this.newPosition, this.piece);//else just put a pawn in that spot if its not touching a end row
                }
                if(current+16==newPosition||current-16==newPosition){//if this move is its first move and is exactly 16 spaces away meaning two rows up
                    pawn.setJustMoved16(true);//set the just moved 16 spaces equal to true so it can save that it just happened
                    board.Occupy(this.newPosition, this.piece);//add that pawn to the board at the spot i want it in 
                    pawn.lastMove(board.returnTotlaMoves());//set the last move to the current total moves
                    
    
                }else{
                    pawn.setJustMoved16(false);//else just set the just moved 16 variable to false and it can never be set again because its already moved
                }
                
                
                
            }else{
                
                lostPiece = board.Occupy(this.newPosition, this.piece);//if this entire time the piece i was trying to move wasnt a pawn then just make it occupy the new spot
                
                
            }
            if(lostPiece!=null){
                board.setLastPieceWasTaken(true);
            }else{
                board.setLastPieceWasTaken(false);
            }

            
            
            for(int i =0;i<64;i++){
                if(board.getTile(i).isOccupied()){
                    board.getTile(i).getPiece().setUnderAttack(false);

                }
            }
            
            return piece;//returns the piece that just made the move
        }
    
        public int newTile(){//return the tile im trying ot move to
            return this.newPosition;
        }
    
        public Piece getLostPiece(){
            return lostPiece;
        }
    
        

        public boolean isCastling(){
            return false;
        }

    

    


    
}
