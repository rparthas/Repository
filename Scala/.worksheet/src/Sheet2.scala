import scala.collection.mutable.ArrayBuffer

object Sheet2 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(98); val res$0 = 
  "New York".partition { _.isUpper };System.out.println("""res0: (String, String) = """ + $show(res$0));$skip(42); 
  var buf = ArrayBuffer(-2, 3, -3, 5, -6);System.out.println("""buf  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(buf ));$skip(40); 
  val (neg, pos) = buf.partition(_ < 0);System.out.println("""neg  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(neg ));System.out.println("""pos  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(pos ));$skip(19); 
  val result = pos;System.out.println("""result  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(result ));$skip(19); val res$1 = 
  result += neg(0);System.out.println("""res1: Sheet2.result.type = """ + $show(res$1));$skip(9); val res$2 = 
  result

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
  };System.out.println("""res2: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$2));$skip(762); 
  val t1 = new Time(20, 25);System.out.println("""t1  : Sheet2.Time = """ + $show(t1 ));$skip(24); 
  val t2 = new Time(19);System.out.println("""t2  : Sheet2.Time = """ + $show(t2 ));$skip(16); val res$3 = 
  t2.before(t1);System.out.println("""res3: AnyVal = """ + $show(res$3));$skip(16); val res$4 = 
  t1.before(t2);System.out.println("""res4: AnyVal = """ + $show(res$4));$skip(18); 
  t1.minutes = 40;$skip(5); val res$5 = 
  t1;System.out.println("""res5: Sheet2.Time = """ + $show(res$5));$skip(29); val res$6 = 
  Time(20, 20) - Time(20, 5);System.out.println("""res6: Int = """ + $show(res$6))}
}
