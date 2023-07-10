package mealplanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ShoppingListFileWriter {


    public ShoppingListFileWriter() {

    }

    public void writeShoppingList(HashMap<String, Integer> shoppingList, String name) {
        File file = new File(name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try (PrintWriter printWriter = new PrintWriter(file)) {
            shoppingList.forEach((key, value) -> {
                if (value > 1) {
                    printWriter.println(key + " x" + value);
                } else {
                    printWriter.println(key);
                }
            });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Saved!");
    }
}
