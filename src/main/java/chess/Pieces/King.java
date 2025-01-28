package chess.Pieces;
/*
 * everything about this class is pretty much done besides for check mates which will be decided in the table class
 * other than that the king cant move when there isnt a place to move
 * the king cant move into a spot that another piece from the other team could just move into and win from right after
 * 
 * 
 * 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.Move;
import chess.Board.SpecialMove;
import chess.Board.Tile;
import chess.Board.Castling;

public class King extends Piece{
    private int[] move_Coordinants = {-9,-8,-7,-1,1,7,8,9};
    private boolean hasMoved;
    private BoardUtils utils = new BoardUtils();
    public King(int position, Alliance alliance){
        super(position, alliance);
        this.hasMoved = false;
       
        
    }
    

    @Override
    public List<Move> calculateMoves(Board board) {
        Alliance a = pieceAlliance==Alliance.White ? Alliance.Black : Alliance.White;
        HashSet<Piece> pieces = board.getActivePieces(a);
        HashSet<Integer> cantMove = new HashSet<>();
        for(Piece p : pieces){
            if(p.getAlliance()==this.pieceAlliance){continue;}
            if(p instanceof King){continue;}//this caused a infinite loop when it wasnt in (think about it)
            for(Move move : p.calculateMoves(board)){
                if(!(p instanceof Pawn)){
                    cantMove.add(move.newTile());
                }
                
                if(p instanceof Pawn&&(move.newTile()!=p.getPosition()+8||move.newTile()!=p.getPosition()-8)){
                    
                    cantMove.add(move.newTile()-1);
                    cantMove.add(move.newTile()+1);
                    
                }
            }
            
        }
        List<Move> legalMoves = new ArrayList<>();
        for(int i : move_Coordinants){
            int current = this.piecePosition;
            Tile t = board.getTile(current+i);
            if(t==null){continue;}
            HashSet<Integer> row = utils.getRow(current+i);//make sure ot check that there is no piece that attacks staright in the row that could hurt me
            HashSet<Integer> column = utils.getColomn(i+current);//make sure there sint a piece that can attack straight that can hurt me
            HashSet<Integer> contains = new HashSet<>();//ad those values to be checked here so i know where to skip over them or not
            if(this.getUnderAttack()){
                int count=0;
                for(Piece p : this.getUnderAttckBy()){

                    
                    if(p.attacksStraight()){
                        if(utils.getRowLabel(i+current)==utils.getRowLabel(p.getPosition())||utils.getColumnLabel(current+i).equals(utils.getColumnLabel(p.getPosition()))){
                            if(board.getTile(i+current).isOccupied()&&!board.getTile(i+current).getPiece().equals(p)){
                                count++;
                            }
                            if(!board.getTile(i+current).isOccupied()){count++;}
                        }
                        if(utils.areOnSameDiagonal(p.getPosition(), this.piecePosition)&&utils.areOnSameDiagonal(i+current, this.piecePosition)&&utils.areOnSameDiagonal(p.getPosition(), i+current)){
                            if(board.getTile(i+current).isOccupied()&&!board.getTile(i+current).getPiece().equals(p)){
                                count++;
                            }
                            if(!board.getTile(i+current).isOccupied()){count++;}
                        }
                    }
                    
                }
                if(count>0){continue;}
                
                
            }
            if(contains.contains(i+current)){continue;}
            
            else if(getRight().contains(i)){continue;}
            else if(getLeft().contains(i)){continue;}
            else if(cantMove.contains(current+i)){continue;}


            else if((i==7||i==-7)&&t.isOccupied()&&t.getPiece().getAlliance()!=this.pieceAlliance){
                legalMoves.add(new Move(current,i+current,this,board));
            }
            else if(getRight().contains(i)){continue;}
            
            
            
            else if(t.getPiece()==null||t.getPiece().getAlliance()==null){
                legalMoves.add(new Move(current,i+current,this,board));
            }

            else if(t.getPiece().getAlliance()!=this.pieceAlliance){


                legalMoves.add(new Move(current,i+current,this,board));
            }
        }
        
        Castling castlingRight = null;
        Castling castlingLeft = null;
        if(!hasMoved&&getUnderAttack()==false){ 
             
            if(this.pieceAlliance==Alliance.Black){
                
                castlingRight = new Castling(this,board.getTile(7),board);//get the rook on the far right of the board
                
                castlingLeft = new Castling(this, board.getTile(0), board);//get the rook on the far left of the board
                
                if(castlingRight.canCastle()){//if the rook can castle
                    SpecialMove  move = new SpecialMove(this.piecePosition,6,this,board);
                    legalMoves.add(move);
                }
                if(castlingLeft.canCastle()){
                    
                    SpecialMove move = new SpecialMove(this.piecePosition,1,this,board);
                    legalMoves.add(move);
                }

                
                
            }
            else if(this.pieceAlliance==Alliance.White){
                
                castlingRight = new Castling(this,board.getTile(63),board);//get the rook on the far right of the board
                castlingLeft = new Castling(this, board.getTile(56), board);//get the rook on the far left of the board
                if(castlingRight.canCastle()){//if the rook can castle
                    SpecialMove  move = new SpecialMove(this.piecePosition,62,this,board);
                    legalMoves.add(move);
                }
                if(castlingLeft.canCastle()){
                    SpecialMove move = new SpecialMove(this.piecePosition,57,this,board);
                    legalMoves.add(move);
                }
            }
        }
        
        
        

        return Collections.unmodifiableList(legalMoves);
    }

     
    
    private HashSet<Integer> getLeft(){
        
        HashSet<Integer> left = new HashSet<>();
        HashSet<Integer> invalid = new HashSet<>();

        
        for(int i = 0;i <64;i+=8){
            left.add(i);
        }
        if(!left.contains(piecePosition)){return new HashSet<>();}
        if(left.contains(this.piecePosition)){
            if(this.pieceAlliance==Alliance.White){
                invalid.add(-1);
                invalid.add(-9);
                
                invalid.add(7);
                

            }
            if(this.pieceAlliance==Alliance.Black){
                invalid.add(-1);
                invalid.add(-9);
                
                invalid.add(7);
                

            }
        }

        

        

        
        return invalid;

        
    }

    private HashSet<Integer> getRight(){
        HashSet<Integer> right = new HashSet<>();
        HashSet<Integer> invalid = new HashSet<>();

        for(int i = 7;i <64;i+=8){
            right.add(i);
        }
        if(right.contains(this.piecePosition)){
            if(this.pieceAlliance==Alliance.White){
                invalid.add(1);
                invalid.add(9);
                
                invalid.add(-7);
            }
            else if(this.pieceAlliance==Alliance.Black){
                    invalid.add(1);
                    invalid.add(9);
                    invalid.add(-7);
                
            }
        }
        
        if(!right.contains(piecePosition)){return new HashSet<>();}
        return invalid;
    }


    

    @Override
    public String toString() {
        return "K";
    }

    public boolean gethasMoved(){
        
        return hasMoved;
    }

    public void sethasMoved(boolean tf){
        hasMoved = tf;
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
