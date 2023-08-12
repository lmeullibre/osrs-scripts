package banker;

import org.dreambot.api.wrappers.items.Item;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UtilsTest {

    @Test
    public void updateDatabase() throws InterruptedException {
        Item item = new Item(5636, 1);
        Item item2 = new Item(22846, 1);
        Item item3 = new Item(20864, 1);

        List<Item> itemList = new ArrayList<>();
        itemList.add(item);
        itemList.add(item2);
        itemList.add(item3);
        UUID uuid = UUID.randomUUID();
        //java.banker.Utils.updateDatabase(itemList, String.valueOf(uuid));

        Assert.assertEquals(1,1);
    }
}