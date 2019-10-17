package edu.kotlin.cook.test

import edu.kotlin.cook.addProduct
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.hasKey
import org.hamcrest.Matchers.hasValue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.math.pow


public class MainTest {
    @Test
    fun `check all overloads`() {
        assertAll("Overloads called from Kotlin",
                { println(addProduct("Name", 5.0, "Desc")) },
                { println(addProduct("Name", 5.0)) },
                { println(addProduct("Name")) }
        )
    }

    @Test
    fun `raise an Int to a power`() {
        assertThat(256, equalTo(2.toDouble().pow(8).toInt()))
    }

    @Test
    fun `create map using infix to function`() {
        val map = mapOf("a" to 1, "b" to 2, "c" to 2)
        assertAll(
                { assertThat(map, hasKey("a")) },
                { assertThat(map, hasKey("b")) },
                { assertThat(map, hasKey("c")) },
                { assertThat(map, hasValue(1)) },
                { assertThat(map, hasValue(2)) })
    }

    @Test
    fun `destructuring a Pair`() {
        val pair = "a" to 1
        val (x,y) = pair

        assertThat(x, `is`("a"))
        assertThat(y, `is`(1))
    }
}