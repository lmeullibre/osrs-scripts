package Trader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {

    private Utils utils;


    public GUI(Utils utilitats) {
        utils = utilitats;
        JFrame g = new JFrame("Auto Trader by Dreamwiver");
        g.setSize(1000, 1000);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Add this so you don't close out DB if you close your GUIt
        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        JLabel description = new JLabel("Please, enter your message. You can leave it in blank");
        JLabel title = new JLabel("DW Autotrader");
        JTextField textField1 = new JTextField();
        ((AbstractDocument) textField1.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (string.matches("[a-zA-Z0-9 ]+")) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (text.matches("[a-zA-Z0-9 ]+")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
        textField1.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                utils.setMessage(textField1.getText());
            }
            public void removeUpdate(DocumentEvent e) {
                utils.setMessage(textField1.getText());
            }
            public void insertUpdate(DocumentEvent e) {
                utils.setMessage(textField1.getText());
            }
        });

        // startButton.setEnabled(utils.isStarted());


        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                utils.start();
                g.dispose();
            }
        });

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setBorder(new EmptyBorder(10, 10, 10, 10));
        description.setBorder(new EmptyBorder(10, 10, 10, 10));
        description.setFont(new Font("Arial", Font.PLAIN, 15));
        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height));
        startButton.setPreferredSize(new Dimension(startButton.getPreferredSize().width,50));
        panel.add(title);
        panel.add(description);
        panel.add(textField1);
        panel.add(startButton);
        g.add(panel);
        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }




}
