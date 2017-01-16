import scala.math._
import scala.collection.mutable.ArrayBuffer


object worksheet1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  2+3                                             //> res0: Int(5) = 5
  var a= 5                                        //> a  : Int = 5
  a += 2
  a                                               //> res1: Int = 7
  "Hello".intersect("world")                      //> res2: String = lo
  val b:BigInt = 2                                //> b  : scala.math.BigInt = 2
  b.pow(100)                                      //> res3: scala.math.BigInt = 1267650600228229401496703205376
  sqrt(10)                                        //> res4: Double = 3.1622776601683795
  1.to(10)                                        //> res5: scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 6, 7
                                                  //| , 8, 9, 10)
  1.to(10).map(sqrt(_))                           //> res6: scala.collection.immutable.IndexedSeq[Double] = Vector(1.0, 1.41421356
                                                  //| 23730951, 1.7320508075688772, 2.0, 2.23606797749979, 2.449489742783178, 2.64
                                                  //| 57513110645907, 2.8284271247461903, 3.0, 3.1622776601683795)
  6.*(7)                                          //> res7: Int(42) = 42
  Int.MaxValue                                    //> res8: Int(2147483647) = 2147483647
  "Mississipi".distinct                           //> res9: String = Misp
  "ram".permutations.toArray                      //> res10: Array[String] = Array(ram, rma, arm, amr, mra, mar)
  "ABC".sum.toInt                                 //> res11: Int = 198
  "A".sum.toInt                                   //> res12: Int = 65
  val result= if (2+3 > 1) "suc"                  //> result  : Any = suc
  for(i<-1 to 10)
  print(i)                                        //> 12345678910
  println()                                       //> 
  
  def isVowel(ch : Char)={
    "aeiou".contains(ch)
  }                                               //> isVowel: (ch: Char)Boolean
  isVowel('f')                                    //> res13: Boolean = false
  def vowels(str : String)= {
    for(i <- str if isVowel(i)) yield i
  }                                               //> vowels: (str: String)String
   vowels("apple")                                //> res14: String = ae
   var buf = ArrayBuffer(-2,3,-3,5,-6)            //> buf  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(-2, 3, -3, 5,
                                                  //|  -6)
   
   var index=for(i <- 0 until buf.length if buf(i) < 0) yield i
                                                  //> index  : scala.collection.immutable.IndexedSeq[Int] = Vector(0, 2, 4)
   index=index.reverse
   
   for(i <- 0 until index.length-1) buf.remove(index(i))
   
   buf                                            //> res15: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(-2, 3, 5)
   
   var in = new java.util.Scanner(new java.net.URL("http://horstmann.com/presentations/livelessons-scala-2016/alice30.txt").openStream())
                                                  //> in  : java.util.Scanner = java.util.Scanner[delimiters=\p{javaWhitespace}+][
                                                  //| position=0][match valid=false][need input=false][source closed=false][skippe
                                                  //| d=false][group separator=\,][decimal separator=\.][positive prefix=][negativ
                                                  //| e prefix=\Q-\E][positive suffix=][negative suffix=][NaN string=\Q?\E][infini
                                                  //| ty string=\Q?\E]
   var count =scala.collection.mutable.Map[String,Int]()
                                                  //> count  : scala.collection.mutable.Map[String,Int] = Map()
   while(in.hasNext()){
   	val word=in.next()
   	count(word) = count.getOrElse(word, 0)+1
   }
   
   count("Alice")                                 //> res16: Int = 223
   count("Rabbit")                                //> res17: Int = 29
   
   in = new java.util.Scanner(new java.net.URL("http://horstmann.com/presentations/livelessons-scala-2016/alice30.txt").openStream())
   var countIm =Map[String,Int]()                 //> countIm  : scala.collection.immutable.Map[String,Int] = Map()
   while(in.hasNext()){
   	val word=in.next()
   	countIm = countIm+( word -> (countIm.getOrElse(word, 0)+1))
   }
   countIm("Alice")                               //> res18: Int = 223
   countIm("Rabbit")                              //> res19: Int = 29
   val words = Array("Mary","little")             //> words  : Array[String] = Array(Mary, little)
   words.groupBy { _.substring(0,1) }             //> res20: scala.collection.immutable.Map[String,Array[String]] = Map(M -> Arra
                                                  //| y(Mary), l -> Array(little))
   words.groupBy { _.length() }                   //> res21: scala.collection.immutable.Map[Int,Array[String]] = Map(4 -> Array(M
                                                  //| ary), 6 -> Array(little))
}