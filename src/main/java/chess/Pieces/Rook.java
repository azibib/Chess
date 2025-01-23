package chess.Pieces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.Move;
import chess.Board.Tile;

public class Rook extends Piece{
    private BoardUtils utils = new BoardUtils();
    private boolean hasMoved = false;
    

    private int[] move_Coordinants = {-8,8,1,-1};

    public Rook(int position, Alliance alliance){
        super(position,alliance);
        this.hasMoved = false;
        
        
    }
    

    
    
    @Override
    public List<Move> calculateMoves(Board board) {
        List<Move> legalMoves = new ArrayList<>();
        for(int i : move_Coordinants){
            
            int current = this.piecePosition;
            Tile t = board.getTile(current+i);
            King k = board.getKing(this.pieceAlliance);
            int difference = k.getPosition()-this.piecePosition;
            if(t==null){continue;}
            if(this.getUnderAttack()){
                //this first one is if theyre in the same column as the king
                if(utils.getColumnLabel(this.piecePosition).equals(utils.getColumnLabel(board.getKing(pieceAlliance).getPosition()))&&utils.getColumnLabel(current).equals(utils.getColumnLabel(getUnderAttckBy().getPosition()))){
                    if(difference<0){
                        int c = piecePosition-8;
                        int d = piecePosition+8;
                        while(c>k.getPosition()){
                            if(board.getTile(c)!=null&&board.getTile(c).isOccupied()&&!board.getTile(c).getPiece().equals(k)){
                                c = 10000;
                            }else{
                               c-=8;
                            }
                        }
                        while(this.getUnderAttack()&&d<this.getUnderAttckBy().getPosition()){
                            if(board.getTile(d)!=null&&board.getTile(d).isOccupied()&&!board.getTile(d).getPiece().equals(this.getUnderAttckBy())){
                                this.setUnderAttack(false, null);

                            }
                            d+=8;
                        }
                        
                        if(c==k.getPosition()&&this.getUnderAttack()){
                            
                            if(i==1||i==-1){continue;}
                        }
                    }else{
                        int c = piecePosition+8;
                        int d = piecePosition-8;
                        while(c<k.getPosition()){
                            if(board.getTile(c)!=null&&board.getTile(c).isOccupied()&&!board.getTile(c).getPiece().equals(k)){
                                c = 10000;
                            }else{
                               c+=8;
                            }
                        }
                        while(this.getUnderAttack()&&d>this.getUnderAttckBy().getPosition()){
                            if(board.getTile(d)!=null&&board.getTile(d).isOccupied()&&!board.getTile(d).getPiece().equals(this.getUnderAttckBy())){
                                this.setUnderAttack(false, null);

                            }
                            d-=8;
                        }
                        
                        if(c==k.getPosition()&&this.getUnderAttack()){
                            
                            if(i==1||i==-1){continue;}
                        }
                    }
                    
                }
                //this is if theyre in the same row as the king 
                else if(utils.getRowLabel(this.piecePosition)==(utils.getRowLabel(board.getKing(pieceAlliance).getPosition()))&&utils.getRowLabel(current)==(utils.getRowLabel(getUnderAttckBy().getPosition()))){
                    if(difference<0){
                        int c = piecePosition-1;
                        int d = piecePosition+1;
                        while(c>k.getPosition()){
                            if(board.getTile(c)!=null&&board.getTile(c).isOccupied()&&!board.getTile(c).getPiece().equals(k)){
                                c = 10000;
                            }else{
                               c--;
                            }
                        }
                        while(d<this.getUnderAttckBy().getPosition()){
                            if(board.getTile(d)!=null&&board.getTile(d).isOccupied()&&!board.getTile(d).getPiece().equals(this.getUnderAttckBy())){
                                this.setUnderAttack(false, null);

                            }
                            d+=1;
                        }
                        
                        if(c==k.getPosition()){
                            
                            if(i==8||i==-8){continue;}
                        }
                    }else{
                        int c = piecePosition+1;
                        int d = piecePosition-1;
                        
                        while(c<k.getPosition()){
                            if(board.getTile(c)!=null&&board.getTile(c).isOccupied()&&!board.getTile(c).getPiece().equals(k)){
                                c = 10000;
                            }else{
                               c++;
                            }
                        }
                        while(d>this.getUnderAttckBy().getPosition()){
                            if(board.getTile(d)!=null&&board.getTile(d).isOccupied()&&!board.getTile(d).getPiece().equals(this.getUnderAttckBy())){
                                this.setUnderAttack(false, null);

                            }
                            d-=1;
                        }
                        if(c==k.getPosition()){
                            
                            if(i==8||i==-8){continue;}
                        }
                    }
                    //this one is just if theyre in the same diagonal 
                }else if(utils.areOnSameDiagonal(this.piecePosition, k.getPosition())&&utils.areOnSameDiagonal(this.piecePosition, this.getUnderAttckBy().piecePosition)&&utils.areOnSameDiagonal(k.getPosition(), this.getUnderAttckBy().piecePosition)){
                    continue;
                }
                else{
                    this.setUnderAttack(false, null);
                }
                
            }
            
            
            
            
            if(t.getPiece()==null||t.getPiece().getAlliance()!=this.pieceAlliance){
                HashSet<Integer> row = utils.getRow(piecePosition);
                if(i==1){
                    while(row.contains(piecePosition+i)){//checks the row to see if the spot exists within the row
                        if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()==this.pieceAlliance){
                            i+=8;//if it gets blocked off by one of its own pieces then we stop adding squares after 
                        }else{
                            if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()!=this.pieceAlliance){//stop move projectiosn if it hits a piece of the other teams
                                board.getTile(current+i).getPiece().setUnderAttack(true, this);
                                legalMoves.add(new Move(current,i+current,this,board));
                                i+=8;
                            }else{
                                legalMoves.add(new Move(current,i+current,this,board));
                                i++;
                            }
                        }
                    }
                }
                if(i==-1){
                    
                    while(row.contains(piecePosition+i)){
                        if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()==this.pieceAlliance){
                            i-=8;//same thing as listed above just for moving within the same row in the other direction
                        }else{
                            if(board.getTile(piecePosition+i).getPiece()!=null&&board.getTile(piecePosition+i).getPiece().getAlliance()!=this.pieceAlliance){//stop move projectiosn if it hits a piece of the other teams
                                board.getTile(current+i).getPiece().setUnderAttack(true, this);
                                legalMoves.add(new Move(current,i+current,this,board));
                                i-=8;
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
                            board.getTile(current+i).getPiece().setUnderAttack(true, this);
                            
                            legalMoves.add(new Move(current,i+current,this,board));
                            i=64;  

                        }else{
                            legalMoves.add(new Move(current,i+current,this,board));
                            i+=8;

                        }
                    }
                }
                if(i==-8){
                    while(i+piecePosition>-1&&board.getTile(i+piecePosition)!=null&&(board.getTile(i+piecePosition).getPiece()==null||board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance)){
                        if(board.getTile(i+piecePosition).getPiece()!=null&&board.getTile(i+piecePosition).getPiece().getAlliance()!=this.pieceAlliance){
                            board.getTile(current+i).getPiece().setUnderAttack(true, this);
                            legalMoves.add(new Move(current,i+current,this,board));
                            i=0;  

                        }else{
                            legalMoves.add(new Move(current,i+current,this,board));
                            i-=8;

                        }
                        
                    }
                }
                
                
            }

            
        }
        return Collections.unmodifiableList(legalMoves);
    }

    
   
    @Override
    public String toString() {
        return "R";
    }
    public boolean hasMoved(){
        return hasMoved;
    }
    public void setMoved(boolean tf){
        hasMoved = tf;
    }




    @Override
    public boolean attacksDiagonal() {
        return false;
    }




    @Override
    public boolean attacksStraight() {
        return true;
    }  

    
}
