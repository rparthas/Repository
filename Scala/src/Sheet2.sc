import scala.collection.mutable.ArrayBuffer

object Sheet2 {
  "New York".partition { _.isUpper }              //> res0: (String, String) = (NY,ew ork)
  var buf = ArrayBuffer(-2,3,-3,5,-6)             //> buf  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(-2, 3, -3, 5,
                                                  //|  -6)
  val (neg,pos)=buf.partition ( _<0 )             //> neg  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(-2, -3, -6)
                                                  //| pos  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(3, 5)
  val result=pos                                  //> result  : scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(3, 5)
  result +=neg(0)                                 //> res1: Sheet2.result.type = ArrayBuffer(3, 5, -2)
  result                                          //> res2: scala.collection.mutable.ArrayBuffer[Int] = ArrayBuffer(3, 5, -2)
}