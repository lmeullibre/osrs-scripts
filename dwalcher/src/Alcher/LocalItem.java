package Alcher;

class LocalItem {
    public int id;
    public int price;
    public int amount;

    public LocalItem(int id, int price, int amount) {
        this.id = id;
        this.price = price;
        this.amount = amount;
    }
    public int getId() {
        return id;
    }
    public double getPrice() {
        return price;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
}