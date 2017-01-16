import scala.collection.mutable.ArrayBuffer

object Sheet2 {
  "New York".partition { _.isUpper }              //> res0: (String, String) = (NY,ew ork)
  var buf = ArrayBuffer(-2, 3, -3, 5, -6)         //> buf  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(-2, 3, -3, 5,
                                                  //|  -6)
  val (neg, pos) = buf.partition(_ < 0)           //> neg  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(-2, -3, -6)
                                                  //| pos  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(3, 5)
  val result = pos                                //> result  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(3, 5)
  result += neg(0)                                //> res1: Sheet2.result.type = ArrayBuffer(3, 5, -2)
  result                                          //> res2: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(3, 5, -2)

  class Time(h: Int, m: Int ) {
    def this(hours: Int) = { this(hours, 0) }
    private var minutesSinceMidnight = h * 60 + m
    def minutes = minutesSinceMidnight % 60
    def hours = minutesSinceMidnight / 60
    def minutes_=(newValue: Int) { minutesSinceMidnight = hours * 60 + newValue }
    def before(time: Time) = {
      if (hours < time.hours) {
        true;
      } else if (time.hours == hours) {
        if (minutes < time.minutes) {
          true;
        }
      } else
        false;
    }
    def -(time: Time) = minutesSinceMidnight - time.minutesSinceMidnight
    override def toString = f"${hours}:${minutes}%02d"

  }
  object Time {
    def apply(hours: Int, minutes: Int) = new Time(hours, minutes)
  }
  val t1 = new Time(20, 25)                       //> t1  : Sheet2.Time = 20:25
  val t2 = new Time(19)                           //> t2  : Sheet2.Time = 19:00
  t2.before(t1)                                   //> res3: AnyVal = true
  t1.before(t2)                                   //> res4: AnyVal = false
  t1.minutes = 40
  t1                                              //> res5: Sheet2.Time = 20:40
  Time(20, 20) - Time(20, 5)                      //> res6: Int = 15
}