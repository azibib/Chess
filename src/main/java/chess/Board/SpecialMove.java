package chess.Board;

import chess.Pieces.King;
import chess.Pieces.Piece;
import chess.Pieces.Rook;

public class SpecialMove extends Move {
    boolean isEnpassant;
    private Piece piece;
    private Board board;
    private int current;
    private int newPosition;
    private Piece lostPiece;
    
    

    public SpecialMove(int current, int newPosition, Piece piece, Board board) {
        super(current, newPosition, piece, board);
        isEnpassant = false;
        this.current = current;
        this.newPosition = newPosition;
        this.piece = piece;
        this.board = board;
        this.lostPiece = null;
        
    }
    @Override
    public Piece execute(){
        
        if(isEnpassant){
                board.unOccupy(current);
            if(newPosition==current+9){//then its black moving to the right
                lostPiece = board.unOccupy(current+1);
           
            }else if(newPosition == current+7){//then its black moving to the left
                lostPiece  = board.unOccupy(current-1);
          
            }
            else if(newPosition == current-9){//then its white moving to the left
                lostPiece = board.unOccupy(current-1);
          
            }
            else if(newPosition == current-7){//then its white moving to the right
          
                lostPiece  = board.unOccupy(current+1);
            }
            if(lostPiece!=null){
                board.setLastPieceWasTaken(true);
            }
            board.Occupy(newPosition, piece);
            return piece;
        }else{//this is for if its a castle move
            
            piece = board.unOccupy(current);

            if(newPosition==6){//if the king is moving to the right
                
                Rook p =(Rook)board.unOccupy(7);
                p.setMoved(true);
                
                
                board.Occupy(5, p);
                
                
            }else if(newPosition==2){//if the king is moving to the left
                Rook p =(Rook)board.unOccupy(0);
                p.setMoved(true);
                
                
                board.Occupy(3, p);
                
            }else if(newPosition==62){//if the king is moving to the right
                
                Rook p =(Rook)board.unOccupy(63);
                p.setMoved(true);
                
                
               
                board.Occupy(61, p);
                
                
            }else{
                Rook p =(Rook)board.unOccupy(56);
                p.setMoved(true);
                
                
                board.Occupy(59, p);
                
            }
            ((King)piece).sethasMoved(true);
            
            board.Occupy(newPosition, piece);
            
            
            return piece;
        }
        
    }

    public boolean isEnpassant(){
        return isEnpassant;
    }
    public boolean isCastle(){
        return !isEnpassant;
    }

    public void setEnpassant(boolean enpassant){
        isEnpassant = enpassant;
    }

    @Override
    public Piece getLostPiece(){
        return lostPiece;
    }

    





}
