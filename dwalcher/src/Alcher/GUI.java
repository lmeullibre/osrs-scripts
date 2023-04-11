package Alcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private Utils utils;
    public GUI(Utils utils){
        JFrame g = new JFrame("frame");
        this.utils=utils;
        JPanel panel1 = new JPanel();
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.setRunning();
                g.dispose();
            }
        });
        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height));
        startButton.setPreferredSize(new Dimension(startButton.getPreferredSize().width,50));
        panel1.add(startButton);
        g.add(panel1);
        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel1, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }
}
