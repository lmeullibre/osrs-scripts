package Lurker;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    private Utils utils;
    private DefaultListModel<String> model;
    private MainClass mainClass;

    public GUI(Utils utils, MainClass mainClass) {
        this.utils = utils;
        this.model = new DefaultListModel<>();
        this.mainClass = mainClass;


        JPanel panel1 = new JPanel();
        JButton startButton = new JButton("Start");
        JTextField minValField = new JTextField();
        JTextField addExcludeField = new JTextField();
        JButton addExcludeButton = new JButton("Add");
        JButton removeExcludeButton = new JButton("Remove");
        JList<String> excludeList = new JList<>(model);

        JLabel minValLabel = new JLabel("Please enter the minimum value of the items to pick up");
        JLabel excludeLabel = new JLabel("Add items to exclude");
        JLabel label2 = new JLabel("Blank means 1 coin");
        JLabel title = new JLabel("GE Lurker");

        JFrame g = new JFrame("frame");
        g.setSize(1000, 1000);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1.add(minValLabel);

        ((AbstractDocument)minValField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, string.replaceAll("[^\\d]", ""), attr);
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                fb.replace(offset, length, text.replaceAll("[^\\d]", ""), attrs);
            }
        });

        minValField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (c == '-' && (minValField.getText().contains("-") || minValField.getText().isEmpty())) {
                    e.consume();
                } else if (!Character.isDigit(c) && c != '-') {
                    e.consume();
                } else {
                    int number = Integer.parseInt(minValField.getText() + c);
                    utils.setMinimum(number);
                }
            }
        });

        minValField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (minValField.getText().isEmpty()) {
                    utils.setMinimum(1);
                }
            }
        });

        addExcludeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item = addExcludeField.getText().trim();
                if (!item.isEmpty() && !model.contains(item)) {
                    model.addElement(item);
                    utils.addExcludedItem(item);
                }
                addExcludeField.setText("");
            }
        });

        removeExcludeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = excludeList.getSelectedValue();
                if (selectedItem != null) {
                    model.removeElement(selectedItem);
                    utils.removeExcludedItem(selectedItem);
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

        g.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainClass.stopScript();
            }
        });

        startButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, startButton.getPreferredSize().height));
        startButton.setPreferredSize(new Dimension(startButton.getPreferredSize().width,50));

        title.setFont(minValLabel.getFont().deriveFont(24.0f));

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        minValLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        excludeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel excludePanel = new JPanel(new GridLayout(1, 2));
        excludePanel.add(addExcludeField);
        excludePanel.add(addExcludeButton);

        JPanel removePanel = new JPanel(new GridLayout(1, 2));
        removePanel.add(excludeList);
        removePanel.add(removeExcludeButton);

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(title);
        panel1.add(minValLabel);
        panel1.add(label2);
        panel1.add(minValField);
        panel1.add(excludeLabel);
        panel1.add(excludePanel);
        panel1.add(new JScrollPane(excludeList));
        panel1.add(removePanel);
        panel1.add(startButton);

        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel1, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }
}