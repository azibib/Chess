package chess.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Clock extends JPanel{
    private int time = 600;
    private Timer timeT;
    JLabel timeLabel = new JLabel();
    
    public Clock() throws InterruptedException{
        this.setPreferredSize(new Dimension(60,20));
        
        timeLabel.setFont(new Font("Small", Font.BOLD, 12));
        
        timeT = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                time--;
                int min = time/60;
                int seconds = time%60;
            
                timeLabel.setText(time/60+ ":"+time%60);
                
                
            }
            
        });
        this.add(timeLabel);
        timeLabel.repaint();
        timeLabel.revalidate();
    
        
        
    }

    public Timer getTimer(){
        return timeT;
    }

        


}
