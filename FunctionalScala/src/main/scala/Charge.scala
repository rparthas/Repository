case class Charge(creditCard: CreditCard, amount: Double) {

  def combine(otherCharge: Charge): Charge = {
    if (otherCharge.creditCard == creditCard) {
      Charge(creditCard, amount + otherCharge.amount)
    }
    throw new Exception("cannot charge different cards")
  }

  def coalesce(charges: scala.List[Charge]): scala.List[Charge] =
    charges.groupBy(_.creditCard).values.map(_.reduce(_ combine _)).toList

}
