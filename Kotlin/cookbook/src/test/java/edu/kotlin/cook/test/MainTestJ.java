package edu.kotlin.cook.test;

import edu.kotlin.cook.MainKt;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;

public class MainTestJ {
    @Test
    void checkOverloads() {
        assertAll("overloads called from Java",
                () -> System.out.println(MainKt.addProduct("Name", 5.0, "Desc")),
                () -> System.out.println(MainKt.addProduct("Name", 5.0)),
                () -> System.out.println(MainKt.addProduct("Name"))
        );
    }
}
