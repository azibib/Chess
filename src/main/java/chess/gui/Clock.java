package chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import chess.Pieces.Alliance;

public class Clock extends JPanel{
    private int time = 600;
    private Timer timeT;
    JLabel timeLabel = new JLabel();
    
    public Clock(Alliance alliance) throws InterruptedException{
        this.setPreferredSize(new Dimension(60,20));
        
        timeLabel.setFont(new Font("Small", Font.BOLD, 12));
        
        timeT = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
                int min = time/60;
                int seconds = time%60;

                if(seconds<10){
                    if(min<1){
                        timeLabel.setForeground(Color.RED);
                        
                    }
                    timeLabel.setText(min+ ":0"+seconds);
                }
                else{
                    if(min<1){
                        timeLabel.setForeground(Color.RED);
                        
                    }
                    timeLabel.setText(min+ ":"+seconds);
                }
                
                if(time==0){
                    timeT.stop();
                    JOptionPane.showMessageDialog(null, alliance + " has won the game" ,"WINNER" ,JOptionPane.NO_OPTION);
                }
                
                
            }

            
            
        });
        
        this.add(timeLabel);
        timeLabel.repaint();
        timeLabel.revalidate();
        
    
        
        
    }

    public Timer getTimer(){
        return timeT;
    }
    public int getTime(){
        return time;
    }

        


}
