package com.dunzo.coffeeMachine.service;

import com.dunzo.coffeeMachine.database.Inventory;
import com.dunzo.coffeeMachine.model.Beverage;
import com.dunzo.coffeeMachine.model.CoffeeMachine;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CoffeeMachineService {

    private static CoffeeMachineService coffeeMachineService;
    public CoffeeMachine coffeeMachine;
    public Inventory inventoryManager;
    private static final int MAX_QUEUED_SIZE = 50;
    private final ThreadPoolExecutor executor;

    /**
     * makes class singleton in nature as this class needs to be instantiated only once
     *
     * @return Instance of CoffeeMachineService
     * @throws IOException
     */
    public static CoffeeMachineService getInstance(final String jsonInput) throws IOException {
        if(coffeeMachineService==null) {
            synchronized (CoffeeMachineService.class) {
                if (coffeeMachineService == null){
                    coffeeMachineService = new CoffeeMachineService(jsonInput);
                }
            }
        }
        return coffeeMachineService;
    }

    private CoffeeMachineService(String jsonInput) throws IOException {
        System.out.println("New Machine has been installed");
        this.coffeeMachine = new ObjectMapper().readValue(jsonInput, CoffeeMachine.class);
        int outlet = coffeeMachine.getMachine().getOutlets().getCount();
        executor = new ThreadPoolExecutor(outlet, outlet, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(MAX_QUEUED_SIZE), new ThreadPoolExecutor.DiscardPolicy());
    }

    public void process() {
        this.inventoryManager = Inventory.getInstance();

        Map<String, Integer> ingredients = coffeeMachine.getMachine().getQuantityMap();

        for (String key : ingredients.keySet()) {
            inventoryManager.addInventory(key, ingredients.get(key));
        }

        Map<String, Map<String, Integer>> beverages = coffeeMachine.getMachine().getBeveragesMap();
        for (String key : beverages.keySet()) {
            Beverage beverage = new Beverage(key, beverages.get(key));
            coffeeMachineService.makeBeverage(beverage);
        }
    }

    public void makeBeverage(Beverage beverage) {
        BeverageServiceTask task = new BeverageServiceTask(beverage);
        executor.execute(task);
    }

    public void stopMachine() {
        executor.shutdown();
    }

}
