package chess.Board;


import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import chess.Pieces.Alliance;
import chess.Pieces.King;
import chess.Pieces.Piece;
import chess.Pieces.Rook;

public class Castling  {
    private Boolean canCastle;
    private Board board;
    private King king;

    public Castling(King king,Tile tile, Board board) {
        this.king = king;
        this.board = board;
        if(king.getAlliance()!=this.board.getTurn()){canCastle=false;}
        //check if theres a rook of the same alliance on its original spot
        canCastle = true;
        if(tile == null){
            canCastle = false;
        }
        else if(tile.getPiece()==null||!(tile.getPiece() instanceof Rook)){
            canCastle = false;
        }else{
            Rook rook = (Rook)tile.getPiece();
            
            if(rook.getAlliance()!=king.getAlliance()){
                canCastle = false;
            }
            else if(rook.hasMoved()||king.gethasMoved()){
                canCastle = false;
            }
            else{
                int number = rook.getPosition();
                if(number>king.getPosition()){
                    for(int i = king.getPosition();i<number;i++){
                        if(i==king.getPosition()||i==number){
                            continue;
                        }
                        if(board.getTile(i).isOccupied()){
                            canCastle = false;
                        }
                    }
                }
                else{
                    for(int i = number;i<king.getPosition();i++){
                        if(i==king.getPosition()||i==number){
                            continue;
                        }
                        if(board.getTile(i).isOccupied()){
                            canCastle = false;
                        }
                    }
                }
                if(canCastle){
                    canCastle = noPiecesCanGetBetween(rook);
                }
            }
            

            
        }
        
       

    }




    public Boolean canCastle(){
        return canCastle;
    }


    private boolean noPiecesCanGetBetween(Rook rook){
        
        HashSet<Piece> pieces = king.getAlliance()==Alliance.White ? board.getActivePieces(Alliance.Black) : board.getActivePieces(Alliance.White);
        
        
        
       
        
        int min = Math.min(king.getPosition(), rook.getPosition());
        int max = Math.max(king.getPosition(), rook.getPosition());
        int[] tilesBetweenRookAndKing = IntStream.range(min + 1, max).toArray();
        Set<Integer> tilesSet = Arrays.stream(tilesBetweenRookAndKing).boxed().collect(Collectors.toSet());
       
        for(Piece p : pieces){
            List<Move> moves = p.calculateMoves(board);
            for(Move move : moves){
                if(tilesSet.contains(move.newTile())){return false;}
            }
            
            
        }
        
        return true;
        


    }

    
    

}