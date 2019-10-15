package edu.kotlin.cook.test

import edu.kotlin.cook.addProduct
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll

public class MainTest {
    @Test
    fun `check all overloads`() {
        assertAll("Overloads called from Kotlin",
                { println(addProduct("Name", 5.0, "Desc")) },
                { println(addProduct("Name", 5.0)) },
                { println(addProduct("Name")) }
        )
    }
}