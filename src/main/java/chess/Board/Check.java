package chess.Board;

import java.awt.List;
import java.util.ArrayList;
import java.util.HashSet;

import chess.Pieces.Alliance;
import chess.Pieces.King;
import chess.Pieces.Piece;

public class Check {
    private Alliance alliance;
    private Board board;
    private King king;
    

    public Check(Alliance alliance, Board board, King king) {
        this.alliance = alliance;
        this.board = board;
        this.king = king;
        
    }



    private boolean inCheck(){

        HashSet<Piece> set = new HashSet<>();
        if(alliance==Alliance.White){
            set = board.getActivePieces(Alliance.Black);
        }
        else{
            set = board.getActivePieces(Alliance.White);
        }
        

        for(Piece p : set){
            for(Move move : p.calculateMoves(board)){
                if(move.newTile()==king.getPosition()){
                    return true;
                }
            }
        }
        return false;
    }


    public boolean isCheckMate(){
        if(inCheck()){
           ArrayList<Move> moves = new ArrayList<>(king.calculateMoves(board));
           if(moves.isEmpty()){
               return true;
           }
        }
        return false;
    }

    public boolean getCheck(){
        return inCheck();
    }   

    public boolean getCheckMate(){
        return isCheckMate();
    }


}
