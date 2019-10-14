import java.text.NumberFormat

fun main(args: Array<String>) {
    println("hello")
    var x = 1
    while (x < 5) {
        println(if (x < 3) "small" else "big")
        x++
    }
    MySingleton.myFunction()
    println(addProduct("pencil"))
    println(addProduct("pen", 2.5, "It is a pen"))

    val jk = Person("JK", "test", "Rowling")
    if (jk.middleName != null) {
        println("Length of middle name is ${jk.middleName.length}")
    }
}

fun addProduct(name: String, price: Double = 0.0, desc: String? = null) =
        "Adding product with $name, ${desc ?: "None"}, and " +
                NumberFormat.getCurrencyInstance().format(price)