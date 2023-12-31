package Alcher;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUI {

    private Utils utils;

    public GUI(Utils utils) {
        this.utils = utils;
        JFrame g = new JFrame("DW Alcher Lite");
        g.setSize(1000, 1000);
        g.setLocationByPlatform(true);
        g.setResizable(false);
        g.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        g.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                utils.kill();
            }
        });

        JPanel panel = new JPanel();

        JButton startButton = new JButton("Start");
        startButton.setToolTipText("<html>Before pressing Start, make sure you are near the Grand Exchange,<br>have enough coins, and have nature runes in your inventory.</html>");
        JLabel titleLabel = new JLabel("DW Alcher Lite");
        JLabel subtitleLabel = new JLabel("by Dreamwiver");
        JLabel descriptionLabel = new JLabel("Maximum coins to spend:");
        JTextField maxCoinsField = new JTextField(10);
        JLabel infoLabel = new JLabel("Blank means max stack");

        // Apply document filter to allow only numeric input and limit to 1,000,000
        ((PlainDocument) maxCoinsField.getDocument()).setDocumentFilter(new NumberOnlyFilter(2147483647));




        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String input = maxCoinsField.getText();
                if (!input.isEmpty()) {
                    int maxCoins = Integer.parseInt(input);
                    utils.setMaxCoins(maxCoins);
                }
                utils.setRunning();
                g.dispose();
            }
        });

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        maxCoinsField.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        titleLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
        subtitleLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        descriptionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        startButton.setMaximumSize(new Dimension(200, 60));
        startButton.setPreferredSize(new Dimension(200, 60));
        maxCoinsField.setMaximumSize(new Dimension(100, maxCoinsField.getPreferredSize().height));
        maxCoinsField.setFont(new Font("Arial", Font.PLAIN, 15));
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        infoLabel.setBorder(new EmptyBorder(0, 10, 10, 10));
        panel.add(titleLabel);
        panel.add(subtitleLabel);
        panel.add(descriptionLabel);
        panel.add(maxCoinsField);
        panel.add(infoLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));  // Adds a 10-pixel-high space
        panel.add(startButton);
        g.add(panel);
        g.getContentPane().setLayout(new BorderLayout());
        g.getContentPane().add(panel, BorderLayout.NORTH);
        g.pack();
        g.setVisible(true);
    }

    // Document filter to allow only numeric input and limit to a maximum value
    private static class NumberOnlyFilter extends DocumentFilter {
        private int maxValue;

        public NumberOnlyFilter(int maxValue) {
            this.maxValue = maxValue;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            String newValue = fb.getDocument().getText(0, fb.getDocument().getLength()) + string.replaceAll("\\D", "");
            int value = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);
            if (value <= maxValue) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            String newValue = fb.getDocument().getText(0, fb.getDocument().getLength()) + text.replaceAll("\\D", "");
            int value = newValue.isEmpty() ? 0 : Integer.parseInt(newValue);
            if (value <= maxValue) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}