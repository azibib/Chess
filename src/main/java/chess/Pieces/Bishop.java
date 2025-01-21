package chess.Pieces;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.Move;
import chess.Board.Tile;

public class Bishop extends Piece{
    private int[] move_Coordinants = {-9,-7,7,9};
    private BoardUtils utils = new BoardUtils();

    public Bishop(int position, Alliance alliance){
        super(position,alliance);
        
    }

    @Override
    public List<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for(int i : move_Coordinants){
            int current = this.piecePosition;
            Tile t = board.getTile(i+current);
            if(t==null){continue;}
            
            if(i==7){
                while (i+piecePosition<64&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)) {
                    if(touchingColumn(i+piecePosition)){//if its touching a column on the edge of the board 
                        if(utils.getRow(piecePosition).contains(i+piecePosition)){//check if moving seven spaces would stil lhave it in the same row
                            i=64;
                        }else{//else add it to the moves set as the last possible psace to move to
                            
                            legalMoves.add(new Move(current, i+current, this, board));
                            i=64;
                        }
                        
                    }
                    else if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){//if the piece at the tile is not null and the piece is not this alliance 
                        
                        legalMoves.add(new Move(current, i+current, this, board));//make this the last possible piece to move to and add it to the list
                        i=64;
                    }else{
                        legalMoves.add(new Move(current, i+current, this, board));//else just add it to the list and increment by seven to find the next available piece
                        i+=7;
                    }
                }
            }
            if(i==-7){
                while (i+piecePosition>0&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)) {
                    if(touchingColumn(i+piecePosition)){
                        if(utils.getRow(piecePosition).contains(i+piecePosition)){
                            i=0;
                        }else{
                            
                            legalMoves.add(new Move(current, i+current, this, board));
                            i=0;
                        }
                        
                    }
                    else if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){
                        
                        
                        
                        legalMoves.add(new Move(current, i+current, this, board));
                        i=0;
                    }else{
                        legalMoves.add(new Move(current, i+current, this, board));
                        i-=7;
                    }
                }
            }
            if(i==9){
                while (i+piecePosition<64&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)) {
                    if(touchingColumn(i+piecePosition)){
                        if(touchingColumn(current)){i+=64;}
                        else{
                            
                            legalMoves.add(new Move(current, i+current, this, board));
                            i=64;
                        }
                        
                    }
                    else if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){
                       
                       
                        
                        legalMoves.add(new Move(current, i+current, this, board));
                        i=64;
                    }else{
                        legalMoves.add(new Move(current, i+current, this, board));
                        i+=9;
                    }
                }
            }
            if(i==-9){
                while (i+piecePosition>=0&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)) {
                    if(touchingColumn(i+piecePosition)){

                        if(touchingColumn(current)){//if its on the right edge or left edge then domnt allow them to go nine spaces in the saem direction
                            
                            i=-65;
                        }
                        else{
                            
                            legalMoves.add(new Move(current, i+current, this, board));
                           
                            i-=65;
                        }
                        
                    }
                    else if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){
                       
                        legalMoves.add(new Move(current, i+current, this, board));
                        i=0;
                    }else{
                        legalMoves.add(new Move(current, i+current, this, board));
                        i-=9;
                    }
                }
            }
            
           

            
        }
        return Collections.unmodifiableList(legalMoves);
    }

    private boolean touchingColumn(int position){//to find out if its touching any edge at all
        int[] left = {0,8,16,24,32,40,48,56};
        int[] right = {7,15,23,31,39,47,55,63};

        for(int i : right){
            if(position==i){return true;}
        }
        for(int i : left){
            if(position==i){return true;}
        }
        return false;
    }
    
    

    

    @Override
    public String toString() {
        return "B";
    }

    @Override
    public boolean attacksDiagonal() {
        return true;
    }

    @Override
    public boolean attacksStraight() {
        return false;
    }
    
}
