package com.dunzo.coffeeMachine.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Machine {

    @JsonProperty("outlets")
    private Outlet outlets;

    @JsonProperty("total_items_quantity")
    private Map<String, Integer> quantityMap;

    @JsonProperty("beverages")
    private Map<String, Map<String, Integer>> beveragesMap;

}
