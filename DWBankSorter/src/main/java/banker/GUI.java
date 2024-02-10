package banker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.List;

import static org.dreambot.api.utilities.Logger.log;

public class GUI {
    public JFrame frame;
    private JComboBox<String> categoryDropdown;
    private JPanel itemPanel;
    private JButton startButton;
    private List<BankItem> customItems; // Add this field to hold the custom items

    private JList<String> selectedCategoriesList;
    private DefaultListModel<String> selectedCategoriesModel;

    private List<BankItem> originalOrder;

    private Utils utils;

    public GUI(List<BankItem> customItems, Utils utils) {
        this.customItems = customItems;
        this.utils = utils;
        this.originalOrder =new ArrayList<>(customItems);
        createAndShowGUI();
    }

    private void updateItemDisplayBasedOnCategories() {
        List<String> selectedCategories = Collections.list(selectedCategoriesModel.elements());


        if (selectedCategories.isEmpty()) {
            customItems = new ArrayList<>(originalOrder);
        } else {
            customItems.sort((item1, item2) -> {
                boolean item1IsInCategory = item1.getCategories().stream().anyMatch(selectedCategories::contains);
                boolean item2IsInCategory = item2.getCategories().stream().anyMatch(selectedCategories::contains);
                return Boolean.compare(item2IsInCategory, item1IsInCategory);
            });
        }

        itemPanel.removeAll();
        customItems.forEach(item -> {
            ImageIcon icon = IconUtils.decodeBase64ToIcon(item.getIcon());
            JLabel itemLabel = new JLabel(icon);
            itemPanel.add(itemLabel);
        });
        itemPanel.revalidate();
        itemPanel.repaint();
    }



    private void createAndShowGUI() {
        frame = new JFrame("Item Sorter");
        frame.setSize(800, 500);

        JLabel selectedCategoriesLabel = new JLabel("Selected Categories");
        selectedCategoriesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        selectedCategoriesModel = new DefaultListModel<>();
        selectedCategoriesList = new JList<>(selectedCategoriesModel);

        Set<String> allCategoriesSet = new TreeSet<>();
        for (BankItem item : customItems) {
            allCategoriesSet.addAll(item.getCategories());
        }

        List<String> allCategoriesList = new ArrayList<>(allCategoriesSet);
        allCategoriesList.add(0, "Select one or multiple categories");

        categoryDropdown = new JComboBox<>(allCategoriesList.toArray(new String[0]));

        categoryDropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) categoryDropdown.getSelectedItem();
                if (selectedCategory != null && !selectedCategory.equals("Select one or multiple categories")) {
                    selectedCategoriesModel.addElement(selectedCategory);
                    updateItemDisplayBasedOnCategories(); // Call after modifying the list
                }
            }
        });


        selectedCategoriesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setText(value.toString() + "    X");
                return label;
            }
        });

        selectedCategoriesList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = selectedCategoriesList.locationToIndex(e.getPoint());
                if (index != -1) {
                    JList list = (JList)e.getSource();
                    DefaultListCellRenderer renderer = (DefaultListCellRenderer) list.getCellRenderer();
                    JLabel label = (JLabel) renderer.getListCellRendererComponent(list, list.getModel().getElementAt(index), index, false, false);
                    Dimension prefSize = label.getPreferredSize();

                    Rectangle rect = list.getCellBounds(index, index);
                    Rectangle xRect = new Rectangle(rect.x + prefSize.width - 20, rect.y, 20, prefSize.height);

                    if (xRect.contains(e.getPoint())) {
                        selectedCategoriesModel.removeElementAt(index);
                        updateItemDisplayBasedOnCategories();
                    }
                }
            }
        });


        JScrollPane listScrollPane = new JScrollPane(selectedCategoriesList);
        listScrollPane.setPreferredSize(new Dimension(200, 100)); // Adjust size as needed
        frame.add(listScrollPane, BorderLayout.EAST);


        itemPanel = new JPanel(new GridLayout(0, 8, 5, 5)); // 0 means any number of rows
        ToolTipManager.sharedInstance().setInitialDelay(0);

        for (BankItem item : customItems) {
            ImageIcon icon = IconUtils.decodeBase64ToIcon(item.getIcon());
            JLabel itemLabel = new JLabel(icon);
            itemLabel.setToolTipText(item.getName());
            itemPanel.add(itemLabel);
        }

        // Buttons
        startButton = new JButton("Start sorting");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSorting();
            }
        });

        JScrollPane scrollPane = new JScrollPane(itemPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        // Layout
        frame.add(categoryDropdown, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void startSorting() {
        List<Integer> sortedItemIds = getSortedItemIds();

        utils.sortedItemIds = sortedItemIds;

        log("Sorted Item IDs: " + sortedItemIds);

    }

    private List<Integer> getSortedItemIds() {
        List<Integer> sortedItemIds = new ArrayList<>();
        for (BankItem item : customItems) {
            sortedItemIds.add(item.getId());
        }
        return sortedItemIds;
    }

}
