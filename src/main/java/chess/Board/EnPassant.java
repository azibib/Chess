package chess.Board;

import java.util.HashSet;

import chess.Pieces.Pawn;
import chess.Pieces.Piece;

public  class EnPassant{
    private BoardUtils utils = new BoardUtils();
    private boolean canEnpassant;
    
    public EnPassant(Pawn p1, Tile p2, Board board){//p1 is alwasy going ot be the pawn that is going to do the enpassant not the one being taken
        if(p1==null||p2==null){
            canEnpassant =false;
        }
        else if(!p2.isOccupied()){//checking if the tile passed in is not occupied then get out of here
            canEnpassant = false;
        }
        else if(p2.getPiece() instanceof Pawn){//if it is occupied and is of type pawn then we get started with the check
            Pawn pawn1 = (Pawn)p1;
            Pawn pawn2 = (Pawn)p2.getPiece();
            if((utils.getRowLabel(pawn1.getPosition())==utils.getRowLabel(pawn2.getPosition()))){//if they are in the same row
                if(utils.getRowLabel(pawn1.getPosition())==5||utils.getRowLabel(pawn1.getPosition())==4){//if they are in either the fourth or the fifth row
                    if(pawn2.getJustMoved16()){//if the one pawssed in second hsa just moved 16 spaces
                        if(pawn2.getLastmove()==board.returnTotlaMoves()){
                            canEnpassant = true;
                        }
                        else{
                            canEnpassant = false;
                        }
                        
                        
                    }
                }
            }
            
        }else{
            canEnpassant =false;
        }
        

    }

    public boolean canEnpassant(){
        return canEnpassant;
    }

    


    
}