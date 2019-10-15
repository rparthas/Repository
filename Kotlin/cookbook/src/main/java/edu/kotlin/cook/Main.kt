package edu.kotlin.cook

import java.text.NumberFormat

fun main(args: Array<String>) {

}

@JvmOverloads
fun addProduct(name: String, price: Double = 0.0, desc: String? = null) =
        "Adding product with $name, ${desc ?: "None"}, and ${NumberFormat.getCurrencyInstance().format(price)}"