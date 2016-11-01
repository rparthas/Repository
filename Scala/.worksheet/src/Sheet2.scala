import scala.collection.mutable.ArrayBuffer

object Sheet2 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(97); val res$0 = 
  "New York".partition { _.isUpper };System.out.println("""res0: (String, String) = """ + $show(res$0));$skip(38); 
  var buf = ArrayBuffer(-2,3,-3,5,-6);System.out.println("""buf  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(buf ));$skip(38); 
  val (neg,pos)=buf.partition ( _<0 );System.out.println("""neg  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(neg ));System.out.println("""pos  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(pos ));$skip(17); 
  val result=pos;System.out.println("""result  : scala.collection.mutable.ArrayBuffer[Int] = """ + $show(result ));$skip(18); val res$1 = 
  result +=neg(0);System.out.println("""res1: Sheet2.result.type = """ + $show(res$1));$skip(9); val res$2 = 
  result;System.out.println("""res2: scala.collection.mutable.ArrayBuffer[Int] = """ + $show(res$2))}
}
