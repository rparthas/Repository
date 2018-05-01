package hello

object Hello {
  def main(args: Array[String]): Unit = {
    println("hi")
    val array: Array[String] = new Array[String](3)
    array(1) = "hi"
    println(array(1))
    dayName(2)
  }

  object DaysOfWeek extends Enumeration {
    val SATURDAY, SUNDAY, MONDAY = Value

    override def toString(): String = this match {
      case SATURDAY => "Saturday"
      case SUNDAY => "Sunday"
      case MONDAY => "Monday"
      case _ => "No Day"
    }
  }

  def dayName(id: Int): String = {
    println(s"Day of week is ${DaysOfWeek(id).toString()}")
    DaysOfWeek(id).toString()
  }
}

