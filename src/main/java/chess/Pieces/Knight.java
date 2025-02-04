package chess.Pieces;
/*
 * 
 * this class is also finsihed from what i understand
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.Move;
import chess.Board.Tile;

public class Knight extends Piece {
    private int[] move_Coordinants = {-17,-15,-10,-6,6,10,15,17};
    private final int initpos;

    public Knight(int position, Alliance alliance){
        super(position,alliance);
        initpos=position;
        
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
            BoardUtils utils = new BoardUtils();
            Tile t = board.getTile(current+i);
            
            if(t==null){continue;}
            
            if(this.getUnderAttack()){
                boolean di = false;
                King k = board.getKing(pieceAlliance);
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
                        HashSet<Integer> betweenKingAndPiece = utils.getPiecesInBetween(k.getPosition(), piece.getPosition());
                        isDiagonal=true;
                        
                        for(int val : betweenKingAndPiece){
                            if(board.getTile(val).isOccupied()&&!board.getTile(val).getPiece().equals(this)){
                                otherPiecebetween=true;
                                
                            }
                            if(utils.areOnSameDiagonal(k.getPosition(),piecePosition+i)){
                                di = true;
                            }
                            
                            
                            
                            
                        }
                    }


                }
                if(!otherPiecebetween&&isColumn){
                    continue;
                }
                else if(!otherPiecebetween&&isRow){
                    continue;
                }
                else if(!otherPiecebetween&&isDiagonal){
                    if(i==-1||i==1||i==-8||i==8){continue;}
                    if(!di){continue;}
                }
                

                
            }
            

            

            
            if(i==-6||i==6){
                if(utils.getRow(current).contains(i+current)){continue;}
                else{
                    if(t.getPiece()!=null&&t.getPiece().getAlliance()==pieceAlliance){continue;}
                    if(board.getTile(i+current).isOccupied()){
                        board.getTile(current+i).getPiece().setUnderAttack(true, this);
                    }
                    legalMoves.add(new Move(current,i+current,this,board));
                }
            }
            else if(i==-10||i==10){
                int currentColumnHead = 64;
                int newColumnHead = 64;
                for(int j : utils.getColomn(this.piecePosition)){
                    if(j<currentColumnHead){currentColumnHead=j;}
                }
                for(int j : utils.getColomn(this.piecePosition+i)){
                    if(j<newColumnHead){newColumnHead=j;}
                }
                if(!(Math.abs(newColumnHead-currentColumnHead)<=4)){
                    continue;
                }
                
                if(t.getPiece()!=null&&t.getPiece().getAlliance()==pieceAlliance){continue;}
                if(board.getTile(i+current).isOccupied()){
                    board.getTile(current+i).getPiece().setUnderAttack(true, this);
                }
                legalMoves.add(new Move(current,i+current,this,board));
                
            }else if(i==-15||i==15){
                int currentColumnHead = 64;
                int newColumnHead = 64;
                for(int j : utils.getColomn(this.piecePosition)){
                    if(j<currentColumnHead){currentColumnHead=j;}
                }
                for(int j : utils.getColomn(this.piecePosition+i)){
                    if(j<newColumnHead){newColumnHead=j;}
                }
                if(!(Math.abs(newColumnHead-currentColumnHead)<=4)){
                    continue;
                }
                
                if(t.getPiece()!=null&&t.getPiece().getAlliance()==pieceAlliance){continue;}
                if(board.getTile(i+current).isOccupied()){
                    board.getTile(current+i).getPiece().setUnderAttack(true, this);
                }
                legalMoves.add(new Move(current,i+current,this,board));
                
            }else if(i==-17||i==17){
                int currentColumnHead = 64;
                int newColumnHead = 64;
                for(int j : utils.getColomn(this.piecePosition)){
                    if(j<currentColumnHead){currentColumnHead=j;}
                }
                for(int j : utils.getColomn(this.piecePosition+i)){
                    if(j<newColumnHead){newColumnHead=j;}
                }
                if(!(Math.abs(newColumnHead-currentColumnHead)<=4)){
                    continue;
                }
                
                if(t.getPiece()!=null&&t.getPiece().getAlliance()==pieceAlliance){continue;}
                if(board.getTile(i+current).isOccupied()){
                    board.getTile(current+i).getPiece().setUnderAttack(true, this);
                }
                legalMoves.add(new Move(current,i+current,this,board));
                
            }
            
            
        }
        
        return Collections.unmodifiableList(legalMoves);
    }

    

    @Override
    public String toString() {
        return "N";
    }

    @Override
    public boolean attacksDiagonal() {
        return false;
    }

    @Override
    public boolean attacksStraight() {
        return false;
    }

    
    
}
