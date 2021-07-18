package com.dunzo.coffeeMachine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Beverage {

    private String beverageName;
    private Map<String, Integer> ingrediantMap;

}
