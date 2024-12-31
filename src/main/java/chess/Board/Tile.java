package chess.Board;

import java.net.URI;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;

import chess.Pieces.Piece;

public abstract class Tile {

    int coordinate;

    Tile(int coordinate){
        this.coordinate = coordinate;
    }
    public abstract boolean isOccupied();

    public abstract Piece getPiece();

    private static Map<Integer, EmptyTile> empty_tile_cahce = new HashMap<>();
        private static Map<Integer, EmptyTile> creatAllEmptyTiles(){
            for(int i =0;i<64;i++){
                empty_tile_cahce.put(i, new EmptyTile(i));
        }
        return Collections.unmodifiableMap(empty_tile_cahce);
    }
    public static Tile createTile( int tilecoordinate, Piece piece){
        return piece!=null ? new OccupiedTile(tilecoordinate, piece) : empty_tile_cahce.get(tilecoordinate);
    }


    public static final class EmptyTile extends Tile{
    
        EmptyTile(int coordinate){
            super(coordinate);
        }
    
        @Override
        public boolean isOccupied() {
            return false;
        }
    
        @Override
        public Piece getPiece() {
            return null;
        }
    }

    public static final class OccupiedTile extends Tile{
        Piece pieceOnTile;

        OccupiedTile(int coordinate, Piece piece){
            super(coordinate);
            this.pieceOnTile = piece;
        }
        @Override
        public boolean isOccupied() {
           return true;
        }

        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }
        
    }


}


