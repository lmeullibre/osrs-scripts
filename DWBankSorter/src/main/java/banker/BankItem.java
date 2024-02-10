package banker;


import javax.swing.*;
import java.util.List;

public class BankItem {
    private int id;
    private String name;
    private String icon;
    private int quantity;
    private List<String> categories;

    public BankItem(int id, String name, String icon, int quantity, List<String> categories) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.quantity = quantity;
        this.categories = categories;
    }


    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public int getQuantity() {
        return quantity;
    }

    public List<String> getCategories() {
        return categories;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
