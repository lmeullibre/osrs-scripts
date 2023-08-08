package banker;

import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutureCallback;
import com.google.api.core.ApiFutures;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import org.dreambot.api.wrappers.items.Item;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class Utils {
   // private static CountDownLatch latch;


    private static DatabaseReference dbRef;

    static {
        try {
            InputStream inputStream = Utils.class.getClassLoader().getResourceAsStream("google-services.json");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(inputStream))
                    .setDatabaseUrl("https://osrs-banker-default-rtdb.europe-west1.firebasedatabase.app/") //replace for database url
                    .build();

            FirebaseApp.initializeApp(options);
            dbRef = FirebaseDatabase.getInstance().getReference();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateDatabase(List<Item> items, String id) throws InterruptedException {
        System.out.println(id);
        if (items == null) {
            System.out.println("The items list is null");
            return;
        }
       // latch = new CountDownLatch(1);

        Map<String, Object> itemsMap = new HashMap<>();
        for (Item item : items) {
            Map<String, Object> itemDetails = new HashMap<>();
            itemDetails.put("name", item.getName());
            itemDetails.put("position", 43);
            itemsMap.put(String.valueOf(item.getID()), itemDetails);
        }

        DatabaseReference itemsRef = dbRef.child("items/"+id);

        ApiFuture<Void> future = itemsRef.setValueAsync(itemsMap);
        try {
            future.get();  // This will block the current thread
            System.out.println("Write successful");
        } catch (Exception e) {
            System.out.println("Write failed: " + e.getMessage());
        }

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("items/"+id);

       // ValueEventListener valueEventListener = new ValueEventListener() {
         //   @Override
        //    public void onDataChange(DataSnapshot dataSnapshot) {
         //       System.out.println("data changed");
         //       Boolean value = dataSnapshot.child("ready").getValue(Boolean.class);
          //      if (value != null && value) {
         //           System.out.println("good item changes");
         //           latch.countDown();
          //      }
          //  }

           // @Override
          //  public void onCancelled(DatabaseError databaseError) {
           //     System.out.println("Error: " + databaseError.getCode());
           // }
        //};
       // ref.addValueEventListener(valueEventListener);

        //latch.await();
    }
}