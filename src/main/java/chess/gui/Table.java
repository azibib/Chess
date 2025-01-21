package chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import java.util.List;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;



import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


import chess.Board.Board;
import chess.Board.BoardUtils;
import chess.Board.Move;
import chess.Board.SpecialMove;
import chess.Board.Tile;

import chess.Pieces.Alliance;
import chess.Pieces.King;
import chess.Pieces.Pawn;
import chess.Pieces.Piece;

public class Table{
    private Board board;
    private final JFrame frame;
    private boolean haspiece;
    private String path ;
    private sidePanel sidePanel;
    private lostPiece lostPiecePanel;
    private BoardUtils utils;

    private HashMap<Integer,JTile> tileMap = new HashMap<>();
    
    private Piece piece;
    private ArrayList<String> moves = new ArrayList<>();
    private ArrayList<Piece> lostPieces = new ArrayList<>();
    private JPanel main = new JPanel();
    

    public Table() throws InterruptedException{
        utils = new BoardUtils();
        
        this.board = new Board();
        this.board.createBoard();
        
        
        ArrayList<Piece> allLostPieces = lostPieces;
        ArrayList<String> allMoves = moves;
        
        
        

        this.frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JButton P1button = new JButton("Undo");
        JButton P2button = new JButton("Undo");
        P1button.setPreferredSize(new Dimension(80,25));
        P2button.setPreferredSize(new Dimension(80,25));

       
        
        

        // Wrap the button in a panel with FlowLayout
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        buttonPanel.setBackground(new Color(191, 133, 90));
        
        
        
        
       
        
        
        
        buttonPanel.add(P1button, BorderLayout.WEST);
        
        
        P1button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Board b = board.undoMove();
                piece = null;
                haspiece= false;
                
                if(b==null){
                    return;
                }if(!allMoves.isEmpty()){
                    allMoves.remove(0);
                    
                }
                if(board.getLastPieceWasTaken()){
                    
                    if(!allLostPieces.isEmpty()){
                        allLostPieces.remove(allLostPieces.size()-1);
                    }
                }
                
                board = b;
                mainPanelSetUP();
                sidePanel.sidePanelSetUP();
                lostPiecePanel.lostPiecePanelSetUP();
                
                
            }
            
        });
        
        
        main.setLayout(new GridLayout(8,8));
        mainPanelSetUP();
        
        
        
        frame.add(main,BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        sidePanel = new sidePanel();
        lostPiecePanel = new lostPiece();
        frame.add(sidePanel, BorderLayout.EAST);
        frame.add(lostPiecePanel, BorderLayout.WEST);
        
        this.frame.setSize(new Dimension(600,600));
        this.frame.setResizable(false);
        this.frame.setVisible(true);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        


        

    }
    private void mainPanelSetUP(){
        main.removeAll();
        
        
        for(int i =0;i<64;i++){
            JTile tile = new JTile(i);
            tileMap.put(i,tile);

            
            
            main.add(tile);
        }
        main.revalidate();
        main.repaint();
    }
    

    
    private class JTile extends JPanel{
        private final int ID;
        private Tile tile;
        
        private String picPath;
        


        JTile( int ID){
            
            this.ID = ID;
            this.tile = board.getTile(ID);
            this.setLayout(new FlowLayout());
            this.setColor();
            this.getPic();
            this.actions();

            
            
            


        }

        

        private void getPic(){
            if(!tile.isOccupied()){return;}
            Piece piece = tile.getPiece();
            
            picPath = "src/main/resources/"+piece.getAlliance().toString().substring(0,1)+piece.toString();
            File file = new File(picPath + ".gif");
            JLabel label = new JLabel();

            try {

                BufferedImage image = ImageIO.read(file);
                ImageIcon icon = new ImageIcon(image);
                Image img = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                icon = new ImageIcon(img);
                label.setIcon(icon);



            } catch (IOException e) {
                
                System.out.println("fuck you");
            }

            
            
            
            this.add(label);
            

            


            
        }

        private void setColor(){
            if(ID<8&&ID%2==0){
                this.setBackground(new Color(181, 101, 51));
                
            }
            else if(ID>7&&ID<16&&ID%2==1){
                this.setBackground(new Color(181, 101, 51));
            }
            else if(ID>15&&ID<24&&ID%2==0){
                this.setBackground(new Color(181, 101, 51));
            }
            else if(ID>23&&ID<32&&ID%2==1){
                this.setBackground(new Color(181, 101, 51));
            }
            else if(ID>31&&ID<40&&ID%2==0){
                this.setBackground(new Color(181, 101, 51));
            }
            else if(ID>39&&ID<48&&ID%2==1){
                this.setBackground(new Color(181, 101, 51));
            }
            else if(ID>47&&ID<56&&ID%2==0){
                this.setBackground(new Color(181, 101, 51));
            }
            else if(ID>55&&ID<64&&ID%2==1){
                this.setBackground(new Color(181, 101, 51));
            }
            
            else{
                this.setBackground(new Color(222, 184, 135));
            }
            
        }
        private void actions(){
            JTile panel = this;
            this.addMouseListener(new MouseAdapter() {
                

                
                @Override
                public void mouseEntered(MouseEvent e){
                    if(haspiece){return;}
                    int i =0;
                    
                    if(!panel.tile.isOccupied()){return;}//if theres no piece on the panel youre currently looking at
                    Piece tempPiece = board.getTile(ID).getPiece();
                    
                    if(panel.tile.getPiece().getAlliance()!=board.getTurn()&&!haspiece){
                        panel.setBackground(new Color(170,69,0,179));
                        
                        
                        panel.revalidate();
                        panel.repaint();
                    }else if(panel.tile.getPiece().getAlliance()!=board.getTurn()&&haspiece){
                        for(Move move : piece.calculateMoves(board)){
                            if(move.newTile()==panel.ID){
                                
                                panel.setBackground(new Color(69, 170, 0, 179));
                                panel.revalidate();
                                panel.repaint();
                            }
                        }
                    }
                    else{
                        
                        if(tempPiece.calculateMoves(board)!=null||!tempPiece.calculateMoves(board).isEmpty()){
                            
                            HashMap<Integer,Tile> allTiles = board.getBlockableSquares();
                            
                            int count = 0;
                            if(board.kingCantMove()&&tempPiece instanceof King){
                                panel.setBackground(new Color(170,69,0,179));
                                return;
                            }
                            if(allTiles!=null){
                                
                                for(Move move : tempPiece.calculateMoves(board)){
                                    if(allTiles.get(move.newTile())!=null){count++;}
                                }
                                if(tempPiece instanceof King){
                                    count++;
                                }
                                
                                if(count==0){
                                    panel.setBackground(new Color(170,69,0,179));//make it red if the king is in check asnd i havent selected a piece yet and this piece cant block it
                                    return;
                                }
                            }
                            
                            
                        
                            
                            
                            panel.setBackground(new Color(0, 255, 0, 128));
                            
                            
                            List<Move> al = tempPiece.calculateMoves(board);
                            if(tempPiece instanceof King&&allTiles!=null){
                                al = board.kingCanMoveToTheseTilesDuringCheck();
                                count = 0;
                            }
                            
                            for(Move move : al){
                                JTile t = tileMap.get(move.newTile());
                                if(t==null){continue;}
                                if(count>0){
                                    
                                    if(allTiles.get(move.newTile())==null){
                                        continue;
                                    }
                                    
                                }
        
                                t.setBackground(new Color(69, 170, 0, 179));;
                                t.revalidate();
                                t.repaint();
                                
        
                            }
                        }
                        
                        panel.revalidate();
                        panel.repaint();
                    }
                    
                }
                @Override
                public void mouseExited(MouseEvent e){
                    if(haspiece){return;}
                    for(JTile tile : tileMap.values()){
                        tile.setColor();
                        
                        tile.revalidate();
                        tile.repaint();
                    }
                    
                    panel.setColor();
                    panel.revalidate();
                    panel.repaint();
                }

                @Override
                public void mouseClicked(MouseEvent e){
                    if(board.getTile(panel.ID).getPiece()!=null&&!haspiece&&board.getTurn()!=board.getTile(panel.ID).getPiece().getAlliance()){return;}//if i clicked a piece when i dont have a piece selected and its not the correct turn for that piece then return
                    

                    //if the panel isnt occupied and i dont have a piece selected or if the panel i selected is occupied and has a piece that isnt my ally and i have a piece selected
                    if((board.getTile(panel.ID).isOccupied()==false&&haspiece)||(board.getTile(panel.ID).isOccupied()&&board.getTile(panel.ID).getPiece().getAlliance()!=board.getTurn()&&haspiece)){
                        boolean valid_move = false;
                        Move m = null;//the move that will be executed
                        
                        HashMap<Integer,Tile> allTiles = board.getBlockableSquares();
                        int count = 0;
                        if(allTiles!=null){
                            
                            for(Move move : piece.calculateMoves(board)){
                                if(allTiles.get(move.newTile())!=null){count++;}
                            }
                            if(piece instanceof King){
                                count++;
                            }
                            if(count==0){return;}
                        }
                        
                        
                        
                        List<Move> al = piece.calculateMoves(board);//get the move calculations for the curremt piece
                        if(piece instanceof King&&allTiles!=null){
                            al = board.kingCanMoveToTheseTilesDuringCheck();
                            count = 0;
                        }
                        for(Move move :al){
                            if(count>0){
                                
                                if(allTiles.get(move.newTile())==null){continue;}
                                
                            }
                            if(move.newTile()==panel.ID){
                                valid_move=true;
                                m = move;//get the move that we want to execute adn set it
                            }
                            JTile t = tileMap.get(move.newTile());
                            if(t==null){continue;}
                            
                            t.setBackground(new Color(69, 170, 0, 179));
                            
                        }
                        if(!valid_move){return;}
                        
                        
                        
                        
                        
                        File file = new File(path+".gif");
                        
                        try {
                            BufferedImage image = ImageIO.read(file);
                            JLabel label = new JLabel();
                            label.setIcon(new ImageIcon(image));
                            haspiece=false;
                            board.addUndo(board);//add it to the list of undoable moves
                            
                            
                            
                            
                            

                            Piece p = null;
                            Piece lost = null;
                            
                            if(m instanceof SpecialMove){
                                SpecialMove move = (SpecialMove)m;
                                p= move.execute();
                                lost = move.getLostPiece();
                                
                            }else{
                                p = m.execute();
                                lost = m.getLostPiece();
                                
                            }
                            if(board.getTurn()==Alliance.White){
                                board.setTurn(Alliance.Black);
                            }else{
                                board.setTurn(Alliance.White);
                            }
                           
                            if(lost!=null){
                                lostPieces.add(lost);
                                lostPiecePanel.lostPiecePanelSetUP();
                                
                            }
                            
                            
                            if(lost!=null &&lost instanceof King){

                                
                                    
                                if(lost.getAlliance()==Alliance.White){
                                    JOptionPane.showMessageDialog(frame, "Black Has Won The Game", "WINNER", JOptionPane.NO_OPTION);
                                    board = new Board();
                                    board.createBoard();
                                    lostPieces = new ArrayList<>();
                                    moves = new ArrayList<>();
                                    mainPanelSetUP();
                                    sidePanel.sidePanelSetUP();
                                    lostPiecePanel.lostPiecePanelSetUP();
                                    
                                }else if(lost.getAlliance()==Alliance.Black){
                                    JOptionPane.showMessageDialog(frame, "White Has Won The Game", "WINNER", JOptionPane.NO_OPTION);
                                    board = new Board();
                                    board.createBoard();
                                    lostPieces = new ArrayList<>();
                                    moves = new ArrayList<>();
                                    mainPanelSetUP();
                                    sidePanel.sidePanelSetUP();
                                    lostPiecePanel.lostPiecePanelSetUP();
                                    
                                }
                                    
                                
                                
                                
                                
                            }else{
                                moves.add(p.toString()+utils.getColumnLabel(p.getPosition())+utils.getRowLabel(p.getPosition()));
                                mainPanelSetUP();
                                sidePanel.sidePanelSetUP();
                                
                                panel.add(label);
                                panel.revalidate();
                                panel.repaint();
                            }
                            
                            
                            
                            
                            
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }


                        
                        
                    }
                    
                    else if(board.getTile(panel.ID).isOccupied()&&!haspiece){//if the psot im clicking on has a piece and i havent picked up a piece yet
                        
                        if(board.getTile(panel.ID).isOccupied()&&board.getTile(panel.ID).getPiece().getAlliance()==board.getTurn()&&haspiece){//if i already have apiece and i just clicked on a piece that was my ally then dont do anything
                            return;
                        }
                        if(board.getTile(panel.ID).isOccupied()&&board.getTile(panel.ID).getPiece().getAlliance()!=board.getTurn()&&haspiece){//if i havent selected a piece and the piece i just clicked on isnt my ally then return
                            return;
                        }
                        
                        if(board.getTile(panel.ID).getPiece().calculateMoves(board).isEmpty()){return;}//if the piece has no moves to make than dont allow it move
                        if(board.kingCantMove()&&board.getTile(panel.ID).getPiece() instanceof King){
                            panel.setBackground(new Color(170,69,0,179));
                            return;
                        }
                        HashMap<Integer,Tile> allTiles = board.getBlockableSquares();
                        int count = 0;
                        piece = board.getTile(panel.ID).getPiece();
                        if(allTiles!=null){
                            
                            for(Move move : board.getTile(panel.ID).getPiece().calculateMoves(board)){
                                if(allTiles.get(move.newTile())!=null){count++;}
                            }
                            if(piece instanceof King){
                                count++;
                            }
                            if(count==0){
                                
                                return;
                            }
                        }
                        
                        
                        
                        boolean instanceofKing = piece instanceof King;
                        
                        List<Move> al = piece.calculateMoves(board);
                        if(instanceofKing&&allTiles!=null){
                            al = board.kingCanMoveToTheseTilesDuringCheck();
                            count=-1;
                            
                        }
                        
                        
                        
                        
                        panel.removeAll();//remove all of the contenets(picture) from the panel
                        panel.revalidate();
                        panel.repaint();
                        haspiece = true;
                        path = panel.picPath;
                        

                        for(Move move : al){
                            JTile t = tileMap.get(move.newTile());//get me the tile so i can paint the background
                            if(t==null){continue;}
                            if(count>0){
                                if(allTiles.get(move.newTile())==null){continue;}
                            }
                            t.setBackground(new Color(69, 170, 0, 179));
                            t.revalidate();
                            t.repaint();

                        }
                        
                    }
                    else{
                        return;
                    }
                    
                    
                    



                    






                }
            });
        }


    }


    private class sidePanel extends JPanel{

        public sidePanel(){
            
            this.setPreferredSize(new Dimension(70,600));
            this.setLayout(new GridLayout(1,2));
            loadPic();
            
            validate();
        }

        public void loadPic(){
            String path1 = "src/main/resources/WK.gif";
            String path2 = "src/main/resources/BK.gif";

            File file1 = new File(path1);
            File file2 = new File(path2);

            try {
                BufferedImage image1 = ImageIO.read(file1);
                BufferedImage image2 = ImageIO.read(file2);

                JPanel p1 = new JPanel();
                

                JLabel l = new JLabel();
                p1.setBackground(new Color(191, 133, 90));
                
                ImageIcon c = new ImageIcon(image1);
                Image im = c.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                l.setIcon(new ImageIcon(im));
                p1.add(l);
                
               

                JPanel p2 = new JPanel();
                p2.setBackground(new Color(191, 133, 90));
                JLabel lab = new JLabel();
                ImageIcon pic = new ImageIcon(image2);
                Image imag = pic.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                lab.setIcon(new ImageIcon(imag));
                p2.add(lab);
                for(int i = 0;i<newMove().size();i++){
                    if(i%2==0){
                        p1.add(newMove().get(i));
                    }
                    else{
                        p2.add(newMove().get(i));
                    }
                }
                
                
                this.add(p1);
                this.add(p2);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        private ArrayList<JPanel> newMove(){
            ArrayList<JPanel> panels = new ArrayList<>();
            for(String move : moves){
                JPanel p = new JPanel();
                p.setPreferredSize(new Dimension(15,18));
                JLabel label = new JLabel(move);
                p.setBackground(new Color(191, 133, 90));
                label.setFont(new Font("small", Font.PLAIN,9));
                p.add(label);
                panels.add(p);
                
            }
            return panels;
            
        
            
            
            
            
            
            
            
            

            

            

            
            
        }
        private void sidePanelSetUP(){
           this.removeAll();
           loadPic();
           revalidate(); 
           repaint();
        }


        
    }



    private class lostPiece extends JPanel{
        private JPanel white;
        private JPanel black;

        public lostPiece(){
            this.setPreferredSize(new Dimension(30,600));
            this.setLayout(new GridLayout(2,1));

            black = new JPanel();
            
            black.setLayout(new BoxLayout(black,BoxLayout.Y_AXIS));
            black.setBackground(new Color(191, 133, 90));
            
            black.add(Box.createVerticalGlue());
            white = new JPanel();
            white.setLayout(new BoxLayout(white,BoxLayout.Y_AXIS));
            white.setBackground(new Color(191, 133, 90));
            white.add(Box.createVerticalGlue());


            for(int i =0;i<allLostPieces().size();i++){
                
                if(lostPieces.get(i).getAlliance()==Alliance.Black){
                    white.add(allLostPieces().get(i));
                    
                }else{
                    black.add(allLostPieces().get(i));
                    
                }
                
                

                
            }
            
            this.add(black,0);
            this.add(white,1);
            
            validate();
        }

        private ArrayList<JPanel> allLostPieces(){
            ArrayList<JPanel> panels = new ArrayList<>();

            for(int i =0;i<lostPieces.size();i++){
                JPanel panel = new JPanel();
                panel.setBackground(new Color(191, 133, 90));
                

                
                File file = new File("src/main/resources/" + lostPieces.get(i).getAlliance().toString().substring(0,1)+lostPieces.get(i).toString()+".gif");

                try {
                    BufferedImage image = ImageIO.read(file);
                    JLabel l = new JLabel();
                    ImageIcon c = new ImageIcon(image);
                    Image im = c.getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
                    l.setIcon(new ImageIcon(im));
                    
                    panel.add(l);
                    panels.add(panel);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return panels;
        }

        private void lostPiecePanelSetUP(){
            white.removeAll();
            black.removeAll();
            
            
            


            for(int i =0;i<allLostPieces().size();i++){
                
                if(lostPieces.get(i).getAlliance()==Alliance.Black){
                    white.add(allLostPieces().get(i));
                    
                }else{
                    black.add(allLostPieces().get(i),black.getComponentCount()-1);
                    
                }
                
                

                
            }
            white.revalidate();
            white.repaint();
            black.revalidate();
            black.repaint();
        }
    }

    




}

