import scala.math._
object worksheet1 {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(83); 
  println("Welcome to the Scala worksheet");$skip(6); val res$0 = 
  2+3;System.out.println("""res0: Int(5) = """ + $show(res$0));$skip(11); 
  var a= 5;System.out.println("""a  : Int = """ + $show(a ));$skip(9); 
  a += 2;$skip(4); val res$1 = 
  a;System.out.println("""res1: Int = """ + $show(res$1));$skip(29); val res$2 = 
  "Hello".intersect("world");System.out.println("""res2: String = """ + $show(res$2));$skip(19); 
  val b:BigInt = 2;System.out.println("""b  : scala.math.BigInt = """ + $show(b ));$skip(13); val res$3 = 
  b.pow(100);System.out.println("""res3: scala.math.BigInt = """ + $show(res$3));$skip(11); val res$4 = 
  sqrt(10);System.out.println("""res4: Double = """ + $show(res$4));$skip(11); val res$5 = 
  1.to(10);System.out.println("""res5: scala.collection.immutable.Range.Inclusive = """ + $show(res$5));$skip(24); val res$6 = 
  1.to(10).map(sqrt(_));System.out.println("""res6: scala.collection.immutable.IndexedSeq[Double] = """ + $show(res$6));$skip(9); val res$7 = 
  6.*(7);System.out.println("""res7: Int(42) = """ + $show(res$7));$skip(15); val res$8 = 
  Int.MaxValue;System.out.println("""res8: Int(2147483647) = """ + $show(res$8));$skip(24); val res$9 = 
  "Mississipi".distinct;System.out.println("""res9: String = """ + $show(res$9));$skip(29); val res$10 = 
  "ram".permutations.toArray;System.out.println("""res10: Array[String] = """ + $show(res$10));$skip(18); val res$11 = 
  "ABC".sum.toInt;System.out.println("""res11: Int = """ + $show(res$11));$skip(16); val res$12 = 
  "A".sum.toInt;System.out.println("""res12: Int = """ + $show(res$12));$skip(33); 
  val result= if (2+3 > 1) "suc";System.out.println("""result  : Any = """ + $show(result ));$skip(29); 
  for(i<-1 to 10)
  print(i);$skip(12); 
  println();$skip(59); 
  
  def isVowel(ch : Char)={
    "aeiou".contains(ch)
  };System.out.println("""isVowel: (ch: Char)Boolean""");$skip(15); val res$13 = 
  isVowel('f');System.out.println("""res13: Boolean = """ + $show(res$13));$skip(74); 
  def vowels(str : String)= {
    for(i <- str if isVowel(i)) yield i
  };System.out.println("""vowels: (str: String)String""");$skip(19); val res$14 = 
   vowels("apple");System.out.println("""res14: String = """ + $show(res$14))}
}
