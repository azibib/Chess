package chess.Pieces;

import java.util.HashSet;
import java.util.List;

import chess.Board.Board;
import chess.Board.Move;

public abstract class Piece{
    protected int piecePosition;
    protected final Alliance pieceAlliance;
    private final int innitPosition;
    private Board board;
    private boolean UnderAttack = false;
    private HashSet<Piece> underAttackBy = new HashSet<>();

    

    Piece(int piecePosition, Alliance pieceAlliance){
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
        this.innitPosition = piecePosition;
    }


    public  abstract List<Move> calculateMoves(Board board);

    public Alliance getAlliance(){
        return pieceAlliance;
    }

    public int getPosition(){
        return piecePosition;
    }

    public void setPosition(int pos){
        this.piecePosition = pos;
    }

    @Override
    public abstract String toString();


    public boolean canBlockCheck(Board board){
        
        return false;
    }

    public abstract boolean attacksDiagonal();

    public abstract boolean attacksStraight();


    
    public void setUnderAttack(boolean bool, Piece piece){
        this.UnderAttack =bool;

        this.underAttackBy.add(piece);
    }
    public void setUnderAttack(boolean bool){
        this.UnderAttack =bool;
        this.underAttackBy = new HashSet<>();
    }

    public boolean getUnderAttack(){
        return UnderAttack;
    }

    public HashSet<Piece> getUnderAttckBy(){
        return this.underAttackBy;
    }

}