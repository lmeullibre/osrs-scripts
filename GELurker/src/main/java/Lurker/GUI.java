package Lurker;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import static java.lang.Integer.parseInt;

public class GUI {
    private JButton startButton;
    private JPanel panel1;
    private JTextField textField1;
    JFrame g = new JFrame();
    private Utils utils;

    public void close(){
        g.dispose();
    }

    public GUI(Utils utilitats) {
        utils = utilitats;

        g.setSize(300, 400);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Add this so you don't close out DB if you close your GUIt
        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel1, BorderLayout.CENTER);
        g.pack();
        g.setVisible(true);

    }

    private void createUIComponents() {
        textField1 = new JTextField();
        ((AbstractDocument)textField1.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string,
                                     AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, string.replaceAll("[^\\d]", ""), attr);
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                                AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length, text.replaceAll("[^\\d]", ""), attrs);
            }
        });

        textField1.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == '-' && (textField1.getText().contains("-") || textField1.getText().isEmpty())) {
                    e.consume();
                } else if (!Character.isDigit(c) && c != '-') {
                    e.consume();
                } else {
                    int number = Integer.parseInt(textField1.getText() + c);
                    utils.setMinimum(number);
                }
            }
        });

        textField1.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (textField1.getText().isEmpty()) {
                    utils.setMinimum(1);
                }
            }
        });

        startButton = new JButton();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.start();
                g.dispose();
            }
        });
    }
}
