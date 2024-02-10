package banker;
import java.io.FileReader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

public class JsonIconReader {
    public static Map<Integer, BankItem> readItemData(String filename) {
        try (FileReader reader = new FileReader(filename)) {
            Gson gson = new Gson();
            Type type = new TypeToken<Map<Integer, BankItem>>(){}.getType();
            Map<Integer, BankItem> itemDataMap = gson.fromJson(reader, type);

            return itemDataMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}