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

    private Utils utils;

    public GUI(Utils utilitats) {
        utils = utilitats;
        JPanel panel1 = new JPanel();
        JButton startButton = new JButton("Start");
        JTextField textField1 = new JTextField();
        JLabel label = new JLabel("Please enter the minimum value of the items to pick up");
        JLabel label2 = new JLabel("Blank means 1 coin");
        JLabel title = new JLabel("GE Lurker");


        JFrame g = new JFrame("frame");
        g.setSize(1000, 1000);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Add this so you don't close out DB if you close your GUIt

        panel1.add(label);

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

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                utils.start();
                g.dispose();
            }
        });


        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height));
        startButton.setPreferredSize(new Dimension(startButton.getPreferredSize().width,50));

        title.setFont(label.getFont().deriveFont(24.0f));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(title);
        panel1.add(label);
        panel1.add(label2);
        panel1.add(textField1);
        panel1.add(startButton);
        g.add(panel1);


        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel1, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }
}
