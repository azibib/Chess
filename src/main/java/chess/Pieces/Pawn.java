package chess.Pieces;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.EnPassant;
import chess.Board.Move;
import chess.Board.SpecialMove;
import chess.Board.Tile;

public class Pawn extends Piece{
    private final int innitPosition;
    private int[] move_Coordinants = {16,8,7,9};
    private BoardUtils utils;
    private boolean justMoved16;
    private int totalTurns=0;
   

    public Pawn(int position, Alliance alliance){
        super(position,alliance);
        innitPosition = position;
        utils=new BoardUtils();
        if(this.pieceAlliance==Alliance.White){
            for(int i = 0;i<move_Coordinants.length;i++){
                move_Coordinants[i] = move_Coordinants[i]*-1;
            }
        }
        justMoved16=false;
        
        
    }

    public Pawn(int position, Alliance alliance, int intitial){
        super(position,alliance);
        innitPosition = intitial;
        utils=new BoardUtils();
        if(this.pieceAlliance==Alliance.White){
            for(int i = 0;i<move_Coordinants.length;i++){
                move_Coordinants[i] = move_Coordinants[i]*-1;
            }
        }
        
        
        
        
    }

    @Override
    public List<Move> calculateMoves(Board board) {
        

        
        
        List<Move> legalMoves = new ArrayList<>();
        for(int i : move_Coordinants){
            int current = this.piecePosition;
            Tile t = board.getTile(current+i);
            King k = board.getKing(pieceAlliance);
            if(t==null){continue;}
            if(this.getUnderAttack()){
                boolean di = false;
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
                if(!otherPiecebetween&&isColumn&&(i==-7||i==7||i==-9||i==-9)){
                    continue;
                }
                else if(!otherPiecebetween&&isRow){
                    continue;
                }
                else if(!otherPiecebetween&&isDiagonal){
                    if(i==-1||i==1||i==-8||i==8||i==16||i==-16){continue;}
                    if(di){continue;}
                }
                

                
            }
            if((i==7||i==-7)&&!t.isOccupied()){continue;}
            else if((i==9||i==-9)&&!t.isOccupied()){continue;}
            else if((i==8||i==-8)&&t.isOccupied()){continue;}
            else if((i==16)&&(t.isOccupied()||t.isOccupied())){continue;}
            else if((i==-16)&&(t.isOccupied()||t.isOccupied())){continue;}
            else if((i==-16||i==16)&&current!=innitPosition){continue;}
            else if(i==-7||i==7||i==9||i==-9){
                HashSet<Integer> currCol = utils.getColomn(current);
                HashSet<Integer> nextCol = utils.getColomn(current+i);
                int currHead = 64;
                int nextHead = 64;
                for(int j : currCol){
                    if(j<currHead){
                        currHead=j;
                    }
                }
                for(int j : nextCol){
                    if(j<nextHead){
                        nextHead=j;
                    }
                }

                if(Math.abs(nextHead-currHead)!=1){
                    continue;
                }else if(board.getTile(i+current).getPiece().getAlliance()==this.pieceAlliance){
                    continue;
                }
                else{
                    legalMoves.add(new Move(current,i+current,this,board));
                }
                

            }

            
            else if(t.getPiece()==null||t.getPiece().getAlliance()==null){
                
                legalMoves.add(new Move(current,i+current,this,board));
            }

            else if(t.getPiece().getAlliance()!=this.pieceAlliance){


                legalMoves.add(new Move(current,i+current,this,board));
            }
        }

        EnPassant p =new EnPassant(this, board.getTile(piecePosition+1),board);
        if(p.canEnpassant()){
            //this is for if the pieces are enpassanting the the right
            if(this.pieceAlliance==Alliance.White){
                SpecialMove move = new SpecialMove(piecePosition,piecePosition-7,this,board);
                move.setEnpassant(true);
                legalMoves.add(move);
            }else{
                SpecialMove move = new SpecialMove(piecePosition,piecePosition+9,this,board);
                move.setEnpassant(true);
                legalMoves.add(move);
            }
            
        }
        EnPassant p2 =new EnPassant(this, board.getTile(piecePosition-1),board);
        if(p2.canEnpassant()){
            //this is for if the pieces are enpassanting to the left
            if(this.pieceAlliance==Alliance.White){
                SpecialMove move = new SpecialMove(piecePosition,piecePosition-9,this,board);
                move.setEnpassant(true);
                legalMoves.add(move);
            }else{
                SpecialMove move = new SpecialMove(piecePosition,piecePosition+7,this,board);
                move.setEnpassant(true);
                legalMoves.add(move);
            }
            
        }
        
        
        return Collections.unmodifiableList(legalMoves);
    }


    

    

    

    

    @Override
    public String toString() {
        return "P";
    }
    

    public int getInit(){
        return innitPosition;
    }


    public void setJustMoved16(boolean tf){
        this.justMoved16=tf;

    }
    public boolean getJustMoved16(){
        return justMoved16;
    }

    public void lastMove(int num){
        totalTurns = num;
    }

    public int getLastmove(){
        return totalTurns;
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
