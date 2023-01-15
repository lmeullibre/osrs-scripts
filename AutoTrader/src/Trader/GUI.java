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
    private JTextField textField1;
    private JButton startButton;
    private JPanel panel;
    private JLabel title;
    private JLabel description;
    JFrame g = new JFrame();
    private Utils utils;


    public GUI(Utils utilitats) {
        utils = utilitats;
        g.setSize(300, 400);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Add this so you don't close out DB if you close your GUIt
        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel, BorderLayout.CENTER);
        g.pack();
        g.setVisible(true);
    }

    public void close(){
        g.dispose();
    }

    private void createUIComponents() {
        startButton = new JButton();
        description = new JLabel();
        description.setBorder(new EmptyBorder(30, 30, 10, 30));
        title = new JLabel();
        title.setFont(title.getFont().deriveFont(24.0f));
        textField1 = new JTextField();
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
        startButton.setPreferredSize(new Dimension(100, 50));

       // startButton.setEnabled(utils.isStarted());


        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                utils.start();
                g.dispose();
            }
        });
    }
}
