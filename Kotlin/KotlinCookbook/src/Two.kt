fun main(args: Array<String>) {
    val jk = Person("JK", null, "Rowling")
    println("Length of middle name is ${jk.middleName?.length ?: 0}")
    println("${jk as? Person}")

    val a: Int = 3
    val b: Long = a.toLong()
    println("value of b is $b")
}