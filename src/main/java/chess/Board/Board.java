package chess.Board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import chess.Board.Tile.EmptyTile;
import chess.Board.Tile.OccupiedTile;
import chess.Pieces.Alliance;
import chess.Pieces.Bishop;
import chess.Pieces.King;
import chess.Pieces.Knight;
import chess.Pieces.Pawn;
import chess.Pieces.Piece;
import chess.Pieces.Queen;
import chess.Pieces.Rook;
import chess.gui.Clock;


public class Board{
    
    
    
    private HashMap<Integer,Tile> board = new HashMap<>();
    Set<Piece> activeWhitePieces = getActivePieces(Alliance.White);
    Set<Piece> activeBlackPieces = getActivePieces(Alliance.Black);
    Player blackpPlayer;
    Player whitePlayer;
    Stack<Board> undoStack;
    private int TotalMoves= 0;
    private boolean lastpiecewastaken;
    private Alliance turn = Alliance.White;
    private BoardUtils utils =new BoardUtils();
        
    
        public Board( HashMap<Integer,Tile> map, Stack<Board> stack,Alliance turn, int TotalMoves){
            if(map!=null&&stack!=null){
                this.board=map;
                undoStack=stack;
                this.turn = turn;
                this.TotalMoves = TotalMoves;
            }
            else{
                throw new IllegalArgumentException("This board cannot be null");
            }
            
            
            
    
            
        }
    
    
        public Board(){
            for(int i =0;i<64;i++){
                board.put(i, new EmptyTile(i));
            }
            this.undoStack = new Stack<Board>();
            
            this.createBoard();
            this.blackpPlayer = new Player(Alliance.Black);
            this.whitePlayer = new Player(Alliance.White);
        }
        public HashSet<Piece> getActivePieces(Alliance alliance){
            HashSet<Piece> set = new HashSet<>();
            for(Tile i : board.values()){
                if(!i.isOccupied()){continue;}
                if(i.getPiece().getAlliance()==alliance){
                    set.add(i.getPiece());
                }
            }
            return set;
        }
    
        
    
        public Tile getTile(int tile){
            return board.get(tile);
        }
    
        public Piece unOccupy(int tile){
            Tile t = this.getTile(tile);
            if(!t.isOccupied()){
                return null;
            }
            Piece p = this.getTile(tile).getPiece();
            board.put(tile,new EmptyTile(tile));
            return p;
        }
        public Alliance getTurn(){
            return this.turn;
        }
    
        public void setTurn(Alliance alliance){
            this.turn = alliance;
        }
    
    
        public Piece Occupy(int tile, Piece piece ){
            
            if(piece.getAlliance()!=this.turn){return null;}
            Piece returner = board.get(tile).getPiece();
            piece.setPosition(tile);
            board.put(tile, new OccupiedTile(tile, piece));
           
            TotalMoves++;
            return returner;
           
        }
    
        public void createBoard(){
            //Black pieces
            board.put(0, new OccupiedTile(0, new Rook(0, Alliance.Black)));
            board.put(1, new OccupiedTile(1, new Knight(1, Alliance.Black)));
            board.put(2, new OccupiedTile(2, new Bishop(2, Alliance.Black)));
            board.put(3, new OccupiedTile(3, new King(3, Alliance.Black)));
            board.put(4, new OccupiedTile(4, new Queen(4, Alliance.Black)));
            board.put(5, new OccupiedTile(5, new Bishop(5, Alliance.Black)));
            board.put(6, new OccupiedTile(6, new Knight(6, Alliance.Black)));
            board.put(7, new OccupiedTile(7, new Rook(7, Alliance.Black)));
            board.put(8, new OccupiedTile(8, new Pawn(8, Alliance.Black)));
            board.put(9, new OccupiedTile(9, new Pawn(9, Alliance.Black)));
            board.put(10, new OccupiedTile(10, new Pawn(10, Alliance.Black)));
            board.put(11,new OccupiedTile(11, new Pawn(11, Alliance.Black)));
            board.put(12, new OccupiedTile(12, new Pawn(12, Alliance.Black)));
            board.put(13, new OccupiedTile(13, new Pawn(13, Alliance.Black)));
            board.put(14, new OccupiedTile(14, new Pawn(14, Alliance.Black)));
            board.put(15, new OccupiedTile(15, new Pawn(15, Alliance.Black)));
            
    
            //White pieces
            board.put(48, new OccupiedTile(48, new Pawn(48, Alliance.White)));
            board.put(49, new OccupiedTile(49, new Pawn(49, Alliance.White)));
            board.put(50, new OccupiedTile(50, new Pawn(50, Alliance.White)));
            board.put(51, new OccupiedTile(51, new Pawn(51, Alliance.White)));
            board.put(52, new OccupiedTile(52, new Pawn(52, Alliance.White)));
            board.put(53, new OccupiedTile(53, new Pawn(53, Alliance.White)));
            board.put(54, new OccupiedTile(54, new Pawn(54, Alliance.White)));
            board.put(55, new OccupiedTile(55, new Pawn(55, Alliance.White)));
            board.put(56, new OccupiedTile(56, new Rook(56, Alliance.White)));
            board.put(57, new OccupiedTile(57, new Knight(57, Alliance.White)));
            board.put(58, new OccupiedTile(58, new Bishop(58, Alliance.White)));
            board.put(59,new OccupiedTile(59, new King(59, Alliance.White)));
            board.put(60, new OccupiedTile(60, new Queen(60, Alliance.White)));
            board.put(61, new OccupiedTile(61, new Bishop(61, Alliance.White)));
            board.put(62, new OccupiedTile(62, new Knight(62, Alliance.White)));
            board.put(63, new OccupiedTile(63, new Rook(63, Alliance.White)));
    
    
    
        }
        private HashMap<Integer,Tile> getBoard(){
            HashMap<Integer, Tile> newMap = new HashMap<>();
            for (Integer key : board.keySet()) {
                Tile originalTile = board.get(key);
                if(originalTile.isOccupied()){
                    if(originalTile.getPiece() instanceof Pawn){
                        boolean b = ((Pawn)board.get(key).getPiece()).getJustMoved16();//just saving the state of wether or not it just moved 16
                        Pawn pawn = new Pawn(board.get(key).getPiece().getPosition(),board.get(key).getPiece().getAlliance(),((Pawn)board.get(key).getPiece()).getInit());
    
                        pawn.setJustMoved16(b);
                        Pawn p = (Pawn)board.get(key).getPiece();
                        pawn.lastMove(p.getLastmove());
                        
                        
                        Tile copyTile = new OccupiedTile(key, pawn);
                        newMap.put(key, copyTile);
                    }
                    else if(originalTile.getPiece() instanceof Rook){
                        Rook rook =  new Rook(board.get(key).getPiece().getPosition(),board.get(key).getPiece().getAlliance());
                        rook.setMoved(((Rook)board.get(key).getPiece()).hasMoved());
                        Tile copyTile = new OccupiedTile(key, rook);
                        newMap.put(key, copyTile);
                    }
                    else if(originalTile.getPiece() instanceof Knight){
                        Tile copyTile = new OccupiedTile(key, new Knight(board.get(key).getPiece().getPosition(),board.get(key).getPiece().getAlliance()));
                        newMap.put(key, copyTile);
                    }
                    else if(originalTile.getPiece() instanceof Bishop){
                        Tile copyTile = new OccupiedTile(key, new Bishop(board.get(key).getPiece().getPosition(),board.get(key).getPiece().getAlliance()));
                        newMap.put(key, copyTile);
                    }
                    else if(originalTile.getPiece() instanceof Queen){
                        Tile copyTile = new OccupiedTile(key, new Queen(board.get(key).getPiece().getPosition(),board.get(key).getPiece().getAlliance()));
                        newMap.put(key, copyTile);
                    }else{
                        King king = new King(board.get(key).getPiece().getPosition(),board.get(key).getPiece().getAlliance());
                        king.sethasMoved(((King)board.get(key).getPiece()).gethasMoved());
                        Tile copyTile = new OccupiedTile(key, king);
                        newMap.put(key, copyTile);
                    }
                    
                }
                else{
                    newMap.put(key, new EmptyTile(key));
                }
                
            }
            return newMap;
        }
        private Stack<Board> getStack(){
            
            return undoStack;
        }
    
        public void addUndo(Board board){
            if(undoStack.size()>0){undoStack.pop();}
            undoStack.push(new Board(board.getBoard(),board.getStack(),board.getTurn(),this.returnTotlaMoves()));
            
            
        }
    
        
    
        public Board undoMove(){
            if(undoStack.size()==0){return null;}
            
            return undoStack.pop();
        }
    
        public int returnTotlaMoves(){
            return TotalMoves;
        }
        /* 
        public void setPiece(Piece piece , int pos){
            if(piece == null){
                throw new IllegalArgumentException("Piece must not be null");
            }
            if(pos<0||pos>63){throw new IllegalArgumentException("Position must be higher than -1 and lower than 64");}
            board.put(pos, new OccupiedTile(pos, piece));
        }

        */
    
    
        public Piece isInCheck(){
            for(Tile tile : board.values()){
                if(tile.isOccupied()){
                    if(tile.getPiece().getAlliance()==turn){
                        if(tile.getPiece() instanceof King){
                            Check check = new Check(tile.getPiece().getAlliance(),this,(King)tile.getPiece());
                            if(check.getCheck()){
                                return tile.getPiece();
                            }
                            else if(check.getCheckMate()){
                                return tile.getPiece();
                            }
                            return null;
                        }
                    }
                }
            }
            return null;
        }
    
    
        public void setLastPieceWasTaken(boolean b) {
            this.lastpiecewastaken=b;
        }
        public boolean getLastPieceWasTaken() {
            return this.lastpiecewastaken;
        }



        public HashSet<Piece> getPieceCheckingKing(){
            King king = (King)this.isInCheck();
            if(king==null){return null;}
            Alliance alliance = king.getAlliance()==Alliance.White ? Alliance.Black : Alliance.White ;

            HashSet<Piece> set = this.getActivePieces(alliance);
            HashSet<Piece> pieces = new HashSet<>();

            for(Piece piece : set){
                for(Move move : piece.calculateMoves(this)){
                    if(board.get(move.newTile()).isOccupied()&&move.newTile()==king.getPosition()){
                        pieces.add(piece);
                    }
                }
            }
            return pieces;
        }


        public HashMap<Integer,Tile> getBlockableSquares(){
            King king = (King) isInCheck();
            if(king ==null){return null;}
            HashSet<Piece> pieces = getPieceCheckingKing();
            HashMap<Integer,Tile> allTiles= new HashMap<>();
            
            //a piece can be moving side to side or diagonal
            //if its side to side then check all the pieces in between both the king and the piece up to the pieces last move
            for(Piece p: pieces){
                
                
                int totalSquaresBetween = king.getPosition()-p.getPosition();
                    
                if(totalSquaresBetween%7==0){
                    if(totalSquaresBetween<0){
                        for(int i=p.getPosition();i>king.getPosition();i-=7){
                                
                            allTiles.put(i,board.get(i));
                        }
                            
                    }else{
                        for(int i=p.getPosition();i<king.getPosition();i+=7){
                            allTiles.put(i,board.get(i));
                        }
                            
                    }
                }else if(totalSquaresBetween%9==0){
                    if(totalSquaresBetween<0){
                        for(int i=p.getPosition();i>king.getPosition();i-=9){
                            allTiles.put(i,board.get(i));
                        }
                            
                    }else{
                        for(int i=p.getPosition();i<king.getPosition();i+=9){
                            allTiles.put(i,board.get(i));
                        }
                            
                    }
                }else if(totalSquaresBetween%8==0){
                    if(totalSquaresBetween<0){
                        for(int i=p.getPosition();i>king.getPosition();i-=8){
                            allTiles.put(i,board.get(i));
                        }
                           
                    }else{
                        for(int i=p.getPosition();i<king.getPosition();i+=8){
                            allTiles.put(i,board.get(i));
                        }
                            
                    }
                }else if(p instanceof Knight){
                    allTiles.put(p.getPosition(),board.get(p.getPosition()));
                }
                else{
                    if(totalSquaresBetween<0){
                        for(int i=p.getPosition();i>king.getPosition();i--){
                            allTiles.put(i,board.get(i));
                        }
                        
                    }else{
                        for(int i=p.getPosition();i<king.getPosition();i++){
                            allTiles.put(i,board.get(i));
                        }
                            
                    }
                }
            }

            return allTiles;
        }


        public boolean kingCantMove(){
            King king = (King) this.isInCheck();//check if the king is in check
            if(king==null){return false;}//if not return false 
            HashMap<Integer,Tile> map = this.getBlockableSquares();//else get the blockable squares
            HashSet<Piece> pieces = this.getPieceCheckingKing();//get the pieces checking the king
            int count = 0;
            ArrayList<Move> allmoves = new ArrayList<>(king.calculateMoves(this));
            for(Move move : allmoves){//for all the moves that the king can make outside of it being in check
                if(pieces.contains(board.get(move.newTile()).getPiece())){//the current problem with this is that it doesnt check for double check
                     return false;
                }//meaning can it attack the piece thats attacking it
                if(map.get(move.newTile())!=null){count++;}
                
                
                
            }
            if(count >= allmoves.size()){
                
                return true;
            }
            
            return false;
            

        }



        public List<Move> kingCanMoveToTheseTilesDuringCheck(){//this is only for letting the king move into spaces which are not occuupied or occupied by a piece threatening the king
            HashSet<Integer> allTilesPiecesCantMoveTo = new HashSet<>();//to check if these are valid tiles later
            List<Move> moves = new ArrayList<>();//hold possible moves
            King king = (King) this.isInCheck();//returns the king that is in check
            if(king==null){return null;}//if the kign is not in check get out of here
            
            HashSet<Piece> pieces = this.getPieceCheckingKing();//get me all the pieces checking the king
            for(Piece p : pieces){//for ech of the pieces checking the king
                for(Move move : p.calculateMoves(this)){//calculate their moves
                    allTilesPiecesCantMoveTo.add(move.newTile());//add the tiles to the invalid tiles set
                    
                }
            }
            for(Move move : king.calculateMoves(this)){//for each move the king can make
                if(!allTilesPiecesCantMoveTo.contains(move.newTile())){//if the invalid tiles set doesnt contain the current tile im looking to move to 
                    moves.add(move);//add that as a possible position
                }
            }
            
            return moves;
        }

       

        /*
         * int this you put in the same allinace of the kign you want 
         *
         */
        public King getKing(Alliance a){
            for(Piece p : this.getActivePieces(a)){
                if(p instanceof King){
                    King king = (King) p;
                    return king;
                }
            }
            return null;
        }


        

        /*i need to check wether or not a piece is blocking the king from being in check

        get a set of all the pieces which are attacking other pieces on the board of the other alliance 

        when ive got those pieces i need to check if the king is in the same row or column or diagonal from the piece being attacked

        */
        

        

    
    



    

}
