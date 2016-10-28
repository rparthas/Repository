import scala.math._
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
  "ram".permutations.toArray
  "ABC".sum.toInt
  "A".sum.toInt
  val result= if (2+3 > 1) "suc"
  for(i<-1 to 10)
  print(i)
  println()
  
  def isVowel(ch : Char)={
    "aeiou".contains(ch)
  }
  isVowel('f')
  def vowels(str : String)= {
    for(i <- str if isVowel(i)) yield i
  }
   vowels("apple")
}