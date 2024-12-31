package chess.Pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.Move;
import chess.Board.Tile;

public class Queen extends Piece{
    private BoardUtils utils = new BoardUtils();
    private int[] move_Coordinants = {-9,-8,-7,-1,1,7,8,9};
    
    public Queen(int positions, Alliance alliance){
        super(positions, alliance);
        if(alliance==Alliance.White){
            for(int i=0;i<move_Coordinants.length;i++){
                move_Coordinants[i] = move_Coordinants[i]*-1;
            }
        }

    }

    @Override
    public List<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        

        for(int i : move_Coordinants){
            int current = this.piecePosition;
            Tile t = board.getTile(i+current);
            
            if(t==null){continue;}
            HashSet<Integer> currentRow = utils.getRow(current);
            if(i==1){
                
                
                while(currentRow.contains(piecePosition+i)){
                    if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()==this.pieceAlliance){
                        i+=8;//if it gets blocked off by one of its own pieces then we stop adding squares after 
                    }
                    else if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()!=this.pieceAlliance){
                        legalMoves.add(new Move(current,i+current,this,board));
                        i+=8;
                    }
                    else{
                        if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()!=this.pieceAlliance){//stop move projectiosn if it hits a piece of the other teams
                            legalMoves.add(new Move(current,i+current,this,board));
                            i+=1;
                        }else{
                            legalMoves.add(new Move(current,i+current,this,board));
                            i++;
                        }
                    }
                }
                
            }
            if(i==-1){
                while(currentRow.contains(i+this.piecePosition)){
                    if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()==this.pieceAlliance){
                        i-=8;//if it gets blocked off by one of its own pieces then we stop adding squares after 
                    }
                    else if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()!=this.pieceAlliance){
                        legalMoves.add(new Move(current,i+current,this,board));
                        i-=8;
                    }
                    else{
                        if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()!=this.pieceAlliance){//stop move projections if it hits a piece of the other teams
                            legalMoves.add(new Move(current,i+current,this,board));
                            i-=1;
                        }else{
                            legalMoves.add(new Move(current,i+current,this,board));
                            i--;
                        }
                    }
                }
            }
            if(i==8){
                while(i<64&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)){
                    if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){
                        legalMoves.add(new Move(current,i+current,this,board));
                        i=64;  

                    }else{
                        legalMoves.add(new Move(current,i+current,this,board));
                        i+=8;

                    }
                }
            }
            if(i==-8){
                while(i+piecePosition>0&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)){
                    if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){
                        legalMoves.add(new Move(current,i+current,this,board));
                        i=0;  

                    }else{
                        legalMoves.add(new Move(current,i+current,this,board));
                        i-=8;

                    }
                    
                }
            }
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
                        legalMoves.add(new Move(current, i+current, this, board));//else just add it to the list and increment by seven to fin dthe next available piece
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
                        if(touchingColumn(current)){
                            i=64;
                        }else{
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
                        if(touchingColumn(current)){
                            i=-65;
                        }else{
                            legalMoves.add(new Move(current, i+current, this, board));
                            i=-65;
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

    private boolean touchingColumn(int position){
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
        return "Q";
    }
    
}