package com.dunzo.coffeeMachine.database;

import com.dunzo.coffeeMachine.model.Beverage;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for storing all the inventory.
 * It acts as a local DB
 */
public class Inventory {

    private final Map<String, Integer> inventoryMap = new HashMap<>();
    private static Inventory inventory;

    private Inventory() {
    }

    /**
     * following singleton pattern here as this class needs to be instantiated only once
     * @return Instance of Inventory class
     */
    public static Inventory getInstance() {
        if(inventory==null) {
            synchronized (Inventory.class) {
                if (inventory == null){
                    inventory = new Inventory();
                }
            }
        }
        return inventory;
    }

    public synchronized boolean checkAndUpdateInventory(Beverage beverage) {
        Map<String, Integer> requiredIngredientMap = beverage.getIngrediantMap();
        boolean isPossible = true;

        for (String ingredient : requiredIngredientMap.keySet()) {
            int ingredientInventoryCount = inventoryMap.getOrDefault(ingredient, -1);
            if (ingredientInventoryCount == -1 || ingredientInventoryCount == 0) {
                System.out.println(beverage.getBeverageName() + " cannot be prepared because " + ingredient + " is not available");
                isPossible = false;
                break;
            } else if (requiredIngredientMap.get(ingredient) > ingredientInventoryCount) {
                System.out.println(beverage.getBeverageName() + " cannot be prepared because " + ingredient + " is not sufficient");
                isPossible = false;
                break;
            }
        }

        if (isPossible) {
            for (String ingredient : requiredIngredientMap.keySet()) {
                int existingInventory = inventoryMap.getOrDefault(ingredient, 0);
                inventoryMap.put(ingredient, existingInventory - requiredIngredientMap.get(ingredient));
            }
        }

        return isPossible;
    }

    public void addInventory(String ingredient, int quantity) {
        int existingInventory = inventoryMap.getOrDefault(ingredient, 0);
        inventoryMap.put(ingredient, existingInventory + quantity);
    }

}
