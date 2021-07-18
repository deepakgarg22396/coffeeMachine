package com.dunzo.coffeeMachine;

import com.dunzo.coffeeMachine.service.CoffeeMachineService;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

@SpringBootTest
class CoffeeMachineApplicationTests {

    CoffeeMachineService coffeeMachineService;

    @Test
    void testInputJson() throws IOException {
        final String filePath = "input.json";
        File file = new File(Objects.requireNonNull(CoffeeMachineService.class.getClassLoader().getResource(filePath)).getFile());
        String json = FileUtils.readFileToString(file, "UTF-8");
        coffeeMachineService = CoffeeMachineService.getInstance(json);
        coffeeMachineService.process();
        Assert.assertEquals(4, coffeeMachineService.coffeeMachine.getMachine().getBeveragesMap().size());
    }

}
