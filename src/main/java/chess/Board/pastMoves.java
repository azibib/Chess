package chess.Board;

import java.util.List;
import java.util.ArrayList;
import java.util.Stack;

public class pastMoves {
    private List<String> moves;

    public pastMoves(){
        this.moves = new ArrayList<>();

    }


    public void addMove(String move){
        this.moves.add(move);
    }
    public String removeMove(){
        return moves.remove(0);
    }

    public List<String> getMoves(){
        return moves;
    }

    public pastMoves(pastMoves moves){
        this.moves = moves.getMoves();
    }




    
    

}
