package Lurker;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class GUI {

    private Utils utils;
    private DefaultListModel<String> nonWantedItemsModel;

    public GUI(Utils utilitats) {
        this.utils = utilitats;
        this.nonWantedItemsModel = new DefaultListModel<>();

        JPanel panel1 = new JPanel();
        JButton startButton = new JButton("Start");
        JTextField textField1 = new JTextField();
        JTextField nonWantedItemField = new JTextField();
        nonWantedItemField.setPreferredSize(new Dimension(200, 25));
        JButton addNonWantedItemButton = new JButton("Add");
        JButton removeNonWantedItemButton = new JButton("Remove");
        JList<String> nonWantedItemsList = new JList<>(nonWantedItemsModel);

        JLabel label = new JLabel("Please enter the minimum value of the items to pick up");
        JLabel label2 = new JLabel("Blank means 1 coin");
        JLabel title = new JLabel("GE Lurker");
        JLabel nonWantedItemLabel = new JLabel("Enter non-wanted items:");

        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new BorderLayout());
        itemPanel.add(nonWantedItemField, BorderLayout.CENTER);
        itemPanel.add(addNonWantedItemButton, BorderLayout.EAST);

        JPanel itemRemovePanel = new JPanel();
        itemRemovePanel.setLayout(new BorderLayout());
        itemRemovePanel.add(new JScrollPane(nonWantedItemsList), BorderLayout.CENTER);
        itemRemovePanel.add(removeNonWantedItemButton, BorderLayout.EAST);

        JFrame g = new JFrame("GE Lurker");
        g.setSize(1000, 1000);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel1.add(label);

        ((AbstractDocument)textField1.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                fb.insertString(offset, string.replaceAll("[^\\d]", ""), attr);
            }
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
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

        addNonWantedItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = nonWantedItemField.getText().trim();
                if (!itemName.isEmpty()) {
                    utils.addNonWantedItem(itemName);
                    nonWantedItemsModel.addElement(itemName);
                    nonWantedItemField.setText(""); // clear the text field after adding the item
                }
            }
        });

        removeNonWantedItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = nonWantedItemsList.getSelectedValue();
                if (selectedItem != null) {
                    nonWantedItemsModel.removeElement(selectedItem);
                    utils.removeNonWantedItem(selectedItem);
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
        nonWantedItemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(title);
        panel1.add(label);
        panel1.add(label2);
        panel1.add(textField1);
        panel1.add(nonWantedItemLabel);
        panel1.add(itemPanel);
        panel1.add(itemRemovePanel);
        panel1.add(startButton);
        g.add(panel1);

        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel1, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }
}