package chess.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Clock extends JPanel implements Runnable{
    private int time = 600;
    private Thread thread;
    private boolean running =true;
    public Clock() throws InterruptedException{

        this.setPreferredSize(new Dimension(40,20));
        

        

    }
    public synchronized void start() {
        running = true;
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stop() {
        running = false;
    }
    @Override
    public void run() {
        while (time > 0) {
            synchronized (this) {
                if (!running) {
                    try {
                        // Wait while paused
                        wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Clock interrupted!");
                        return;
                    }
                }
            }

            // Timer logic when running
            int min = time / 60;
            int sec = time % 60;

            // Update label
            JLabel label = new JLabel(String.format("%02d:%02d", min, sec));
            this.removeAll();
            this.add(label);
            this.revalidate();
            this.repaint();

            // Decrement time
            time--;

            try {
                Thread.sleep(1000); // Delay 1 second
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Clock interrupted!");
                return;
            }
        }
    }
    public synchronized void resumeClock() {
        running = true;
        notify(); // Wake the thread from wait()
    }


}
