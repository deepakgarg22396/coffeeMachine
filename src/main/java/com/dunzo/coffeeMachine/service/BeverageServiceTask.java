package com.dunzo.coffeeMachine.service;

import com.dunzo.coffeeMachine.database.Inventory;
import com.dunzo.coffeeMachine.model.Beverage;

public class BeverageServiceTask implements Runnable {

    private final Beverage beverage;

    public BeverageServiceTask(Beverage beverage) {
        this.beverage = beverage;
    }

    @Override
    public void run() {
        if (Inventory.getInstance().checkAndUpdateInventory(beverage)) {
            System.out.println(beverage.getBeverageName() + " is prepared");
        }
    }
}
