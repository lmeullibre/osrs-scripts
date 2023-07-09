package Alcher;
import org.dreambot.api.wrappers.items.Item;

import java.util.*;

public class Utils {
    private boolean isRunning;

    private boolean killed;
    private double mistakeRate;

    private int activeItemQuantity;
    private Queue<Item> profitableItems;  // Changed from List to Queue

    private int maxCoins;
    private int spentCoins;
    public Utils() {
        isRunning = false;
        killed = false;
        activeItemQuantity = -1;
        mistakeRate = 0.1;
        maxCoins = 20000;
        spentCoins = 0;
        profitableItems = new LinkedList<>();  // Initialized as a LinkedList, which is a type of Queue
    }

    public boolean isRunning() {
        return isRunning;
    }

    public boolean isKilled(){return this.killed;}
    public void kill(){
        this.killed = true;
    }
    public void setRunning() {
        this.isRunning = true;
    }

    public void addProfitableItem(Item item) {
        profitableItems.add(item);
    }

    public Queue<Item> getProfitableItems() {  // Return type changed to Queue
        return profitableItems;
    }

    public int getMaxCoins(){
        return maxCoins;
    }

    public void setMaxCoins(int coins){
        this.maxCoins = coins;
    }

    public void loadProfitableItems() {
        JSONLoader.loadProfitableItems(this);
    }

    public int getActiveItemQuantity() {
        return this.activeItemQuantity;
    }

    public void increaseItemQuantity(int amount){
        this.activeItemQuantity = this.activeItemQuantity + amount;
    }

    public void setActiveItemQuantity(int amount){
        this.activeItemQuantity = amount;
    }

    public double getMistakeRate() {
        return this.mistakeRate;
    }

    public int getSpentCoins() {
        return this.spentCoins;
    }

    public void addSpentCoins(int amount) {
        this.spentCoins += amount;
    }

    public boolean isOutOfMoney() {
        return this.spentCoins >= this.maxCoins;
    }

    public void clearProfitableItems() {
        profitableItems.clear();
    }

    public Item getActiveItem() {
        return profitableItems.peek();  // Returns the item at the head of the queue
    }

    public void nextActiveItem() {
        profitableItems.poll();  // Removes the item at the head of the queue
    }
}