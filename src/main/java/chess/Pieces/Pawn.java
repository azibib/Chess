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
            
            if(t==null){continue;}
            else if((i==7||i==-7)&&!t.isOccupied()){continue;}
            else if((i==9||i==-9)&&!t.isOccupied()){continue;}
            else if((i==8||i==-8)&&t.isOccupied()){continue;}
            else if((i==16)&&(t.isOccupied()||board.getTile(current+8).isOccupied())){continue;}
            else if((i==-16)&&(t.isOccupied()||board.getTile(current-8).isOccupied())){continue;}
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



    
}
