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
            
            if(t==null){continue;}
            if(this.getUnderAttack()){
                boolean otherPiecebetween = false;
                boolean isColumn = false;
                boolean isRow = false;
                boolean isDiagonal = false;
                for(Piece piece : this.getUnderAttckBy()){
                    //check if the king and the attacking piece and the current piece im trying ot move are on the same column
                    if(utils.getColumnLabel(this.piecePosition).equals(utils.getColumnLabel(k.getPosition()))&&utils.getColumnLabel(piece.getPosition()).equals(utils.getColumnLabel(k.getPosition()))){
                        HashSet<Integer> betweenKingAndPiece = utils.getPiecesInBetween(k.getPosition(), piece.getPosition());
                        isColumn=true;
                        for(int val : betweenKingAndPiece){
                            if(board.getTile(val).isOccupied()&&!board.getTile(val).getPiece().equals(this)){
                                otherPiecebetween=true;
                            }
                        }
                    }
                    else if(utils.getRowLabel(current)==utils.getRowLabel(k.getPosition())&&utils.getRowLabel(k.getPosition())==utils.getRowLabel(piece.getPosition())){
                        HashSet<Integer> betweenKingAndPiece = utils.getPiecesInBetween(k.getPosition(), piece.getPosition());
                        isRow=true;
                        for(int val : betweenKingAndPiece){
                            if(board.getTile(val).isOccupied()&&!board.getTile(val).getPiece().equals(this)){
                                otherPiecebetween=true;
                            }
                        }
                    }
                    else if(utils.areOnSameDiagonal(current, piece.getPosition())&&utils.areOnSameDiagonal(piece.getPosition(), k.getPosition())){
                        //the utils to get the diagonal is messed up i haev to check whats going on it with it
                    }


                }
                if(!otherPiecebetween&&isColumn&&(i==-1||i==1)){
                    continue;
                }
                else if(!otherPiecebetween&&isRow&&(i==-8||i==8)){
                    continue;
                }
                else if(!otherPiecebetween&&isDiagonal){
                    continue;
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
