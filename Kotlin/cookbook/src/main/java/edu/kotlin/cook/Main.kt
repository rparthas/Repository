package edu.kotlin.cook

import java.text.NumberFormat
import kotlin.math.pow

fun main(args: Array<String>) {
    println("power is ${2.pow(4)}")
    println("power is ${2L.pow(4)}")
    println("power is ${2.0f.pow(4)}")
    println("power is ${2.0.pow(4)}")
    println("power is ${2 `**` 4}")

}

infix fun Int.`**`(x: Int) = toDouble().pow(x).toInt()
infix fun Long.`**`(x: Int) = toDouble().pow(x).toLong()
infix fun Float.`**`(x: Int) = pow(x)
infix fun Double.`**`(x: Int) = pow(x)

// Pattern similar to existing functions on Float and Double
fun Int.pow(x: Int) = `**`(x)

fun Long.pow(x: Int) = `**`(x)

@JvmOverloads
fun addProduct(name: String, price: Double = 0.0, desc: String? = null) =
        "Adding product with $name, ${desc ?: "None"}, and ${NumberFormat.getCurrencyInstance().format(price)}"