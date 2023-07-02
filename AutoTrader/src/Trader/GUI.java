package Trader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GUI {

    private Utils utils;
    private DefaultListModel<String> model;



    public GUI(Utils utils) {
        this.utils = utils;
        JFrame g = new JFrame("Auto Trader by Dreamwiver");
        g.setSize(1000, 1000);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JLabel description = new JLabel("Please, enter your messages. You can leave it in blank");
        JLabel title = new JLabel("DW Autotrader");
        JTextField textField1 = new JTextField();
        model = new DefaultListModel<>();
        JList<String> messageList = new JList<>(model);
        JScrollPane scrollPane = new JScrollPane(messageList);
        JButton addButton = new JButton("Add Message");
        Dimension preferredSize = new Dimension(75, 25);  // you can change the width and height to your liking
        addButton.setPreferredSize(preferredSize);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String message = textField1.getText();
                if (!message.trim().isEmpty()) {  // replace isBlank() with trim().isEmpty()
                    utils.addMessage(message);
                    model.addElement(message);
                    textField1.setText("");
                }
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = textField1.getText();
                    if (!message.trim().isEmpty()) {
                        utils.addMessage(message);
                        model.addElement(message);
                        textField1.setText("");
                    }
                }
            }
        });

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                utils.start();
                g.dispose();
            }
        });

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBorder(new EmptyBorder(10, 10, 10, 10));
        description.setBorder(new EmptyBorder(10, 10, 10, 10));
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height));
        startButton.setPreferredSize(new Dimension(startButton.getPreferredSize().width,50));
        panel.add(title);
        panel.add(description);
        JPanel messagesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel messagesLabel = new JLabel("Messages:");
        messagesPanel.add(messagesLabel);
        panel.add(messagesPanel);
        panel.add(scrollPane);
        panel.add(textField1);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Adds a 10-pixel-high space
        panel.add(addButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Adds a 10-pixel-high space
        panel.add(startButton);
        g.add(panel);
        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }
}