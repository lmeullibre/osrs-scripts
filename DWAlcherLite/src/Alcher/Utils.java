package Alcher;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.grandexchange.LivePrices;
import org.dreambot.api.wrappers.items.Item;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static org.dreambot.api.methods.input.mouse.MouseSettings.setSpeed;

public class Utils {
    private boolean isRunning;

    public boolean shouldStop;

    private boolean killed;

    public boolean isReady;

    private String status;

    private int maxCoinsPerSet;

    private double mistakeRate;

    private int earnedCoins;

    private int activeItemQuantity;
    private Queue<Item> profitableItems;  // Changed from List to Queue

    private int natureRunePrice;
    private Instant startTime;
    private int alchsPerformed;

    private int maxCoins;

    private int spentCoins;
    public Utils() {
        isRunning = false;
        killed = false;
        activeItemQuantity = -1;
        mistakeRate = 0.1;
        spentCoins = 0;
        profitableItems = new LinkedList<>();  // Initialized as a LinkedList, which is a type of Queue
        maxCoinsPerSet = 40000;
        earnedCoins = 0;
        maxCoins = 2147483647;
        status = "idle";
        shouldStop = false;
        isReady = false;
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


    public void setRandomMouseSpeed() {
        // Set the mouse speed to a random value within a range that resembles human-like behavior
        int minSpeed = 10; // Minimum speed that is still human-like
        int maxSpeed = 20; // Maximum speed that is still human-like
        int randomSpeed = Calculations.random(minSpeed, maxSpeed);
        setSpeed(randomSpeed);
    }

    public Item getActiveItem() {
        return profitableItems.peek();  // Returns the item at the head of the queue
    }

    public void nextActiveItem() {
        profitableItems.poll();  // Removes the item at the head of the queue
    }

    public void startTiming() {
        this.startTime = Instant.now();
    }

    public void incrementAlchsPerformed() {
        this.alchsPerformed++;
    }

    public void incrementProfit(int coins){
        earnedCoins = earnedCoins + coins;
    }

    public double getAlchsPerHour() {
        if (startTime == null) {
            return 0;
        }
        Instant now = Instant.now();
        Duration timeElapsed = Duration.between(startTime, now);
        double hoursElapsed = timeElapsed.toMillis() / 1000.0 / 60.0 / 60.0;
        return alchsPerformed / hoursElapsed;
    }

    public int getMaxCoinsPerSet() {
        return this.maxCoinsPerSet;
    }

    public int getProfit(){
        return this.earnedCoins -this.spentCoins;
    }

    public void setNatureRunePrice(int natureRunePrice) {
        this.natureRunePrice = natureRunePrice;
    }


    public int getNatureRunePrice(){
        return this.natureRunePrice;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

    public void setIsReady() {
        this.isReady = true;
    }

    public boolean isReady() {
        return this.isReady;
    }
}