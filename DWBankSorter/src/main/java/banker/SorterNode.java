package banker;
import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.Bank;
import org.dreambot.api.wrappers.items.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SorterNode extends Node {
    private Utils utils;

    public SorterNode(Utils utils) {
        super(utils);
        this.utils = utils;
    }

    @Override
    public boolean accept() {
        return utils.sortedItemIds != null && !utils.sortedItemIds.isEmpty() && Bank.isOpen();
    }

    @Override
    public int priority() {
        return 1;
    }

    @Override
    public int execute() {
        if (!Bank.isOpen()) {
            Bank.open();
            return Calculations.random(1000, 3000);
        }

        // Fetch bank items each time to ensure the list is up-to-date
        List<Item> bankItems = Bank.all();
        Map<Integer, Integer> itemIDToSlot = new HashMap<>();

        for (int i = 0; i < bankItems.size(); i++) {
            Item item = bankItems.get(i);
            if (item != null) {
                itemIDToSlot.put(item.getID(), i);
            }
        }

        // Iterate through the sorted list and rearrange items in the bank
        for (int sortedIndex = 0; sortedIndex < utils.sortedItemIds.size(); sortedIndex++) {
            Integer itemId = utils.sortedItemIds.get(sortedIndex);
            Integer currentSlot = itemIDToSlot.get(itemId);

            if (currentSlot != null && currentSlot != sortedIndex) {
                Bank.drag(currentSlot, sortedIndex); // Drag item to its correct position
                sleep(Calculations.random(300, 600)); // Add a delay to mimic human behavior and avoid triggering anti-botting mechanisms
                return Calculations.random(1000, 3000); // Return a random delay before the next loop iteration
            }
        }

        utils.sortedItemIds.clear(); // Clear the list after sorting
        return Calculations.random(1000, 3000);
    }

}
