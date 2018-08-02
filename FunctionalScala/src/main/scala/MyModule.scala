import List._

object MyModule {

  def abs(number: Int): Int = {
    if (number <= 0)
      -number
    else
      number
  }


  def display(name: String, number: Int, f: Int => Int): String = {
    val msg = "%s of %d is %d"
    msg.format(name, number, f(number))
  }

  def factorial(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int, acc: Int): Int = {
      if (n <= 0)
        acc
      else
        go(n - 1, n * acc)
    }

    go(n, 1)
  }


  def findFirst[A](array: Array[A], p: A => Boolean): Int = {
    def loop(index: Int): Int = {
      if (index >= array.length)
        -1
      else if (p(array(index)))
        index
      else
        loop(index + 1)
    }

    loop(0)
  }


  def fib(n: Int): Int = {
    @annotation.tailrec
    def go(n: Int, prev: Int, next: Int): Int = {
      if (n == 0)
        prev
      else
        go(n - 1, next, next + prev)
    }

    go(n - 1, 0, 1)
  }

  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {

    def loop(index: Int, acc: Boolean): Boolean = {
      if (index + 1 >= as.length || !acc)
        acc
      else
        loop(index + 1, acc && ordered(as(index), as(index + 1)))
    }

    loop(0, acc = true)

  }

  def partial1[A, B, C](a: A, f: (A, B) => C): B => C =
    (b: B) => f(a, b)

  def curry[A, B, C](f: (A, B) => C): (A, B) => C = {
    (a: A, b: B) => f(a, b)
  }

  def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
    (a: A, b: B) => f(a)(b)
  }

  def partialAdd(a: Int): (Int) => Int = {
    (b) => {
      add(a, b)
    }
  }

  def compose[A, B, C](f: B => C, g: A => B): A => C = {
    (a: A) => f(g(a))
  }

  def add(a: Int, b: Int): Int = a + b

  def ordered(a: Int, b: Int): Boolean = a < b

  def equal(a: Int): Boolean = a == 2


  def main(args: Array[String]): Unit = {
    println("UnCurry Example:" + uncurry(partialAdd)(2, 3))
    println("Curry Example:" + curry(add)(2, 3))
    println("Compose Example:" + compose(partialAdd(2), partialAdd(3))(4))
    println(display("fibonacci", 10, fib))
    println(display("abs", -42, abs))
    println("findFirst based on condition is " + findFirst(Array(1, 2, 3, 4), equal))
    println("isSorted based on ordered is" + isSorted(Array(1, 3, 2, 4), ordered))
    println("partial function " + partial1(4, add)(2))


    def ints: List[Int] = List(1, 2, 3, 4, 5)

    println("Sum is " + sum(ints))
    println("Product is " + product(ints))
    println("Sum of tail is " + sum(tail(ints)))
    println("Sum of setHead is " + sum(setHead(ints, 6)))
    println("Drop value is " + drop(ints, 3))
    println("Drop While value is " + dropWhile(ints, (a: Int) => a <= 3))
    println("init is " + init(ints))

    val x = List(1, 2, 3, 4, 5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x
      case Nil => 42
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
      case Cons(h, t) => h + sum(t)
      case _ => 101
    }
    println("x is " + x)

    println("Merge two lists is "+foldRight(List(1,2,3), Nil:List[Int])(Cons(_,_)))
    println("Length of list is "+length(List(1,2,3)))

    println("Sum is " + sum3(ints))
    println("Product is " + product3(ints))
    println("Reverse is " + reverse(ints))


    println("Add impl is"+ map(ints)(a=> a+1))
    println("Even List is"+ filter(ints)(a=> a%2 == 0))

    println("FlatMap example "+ flatMap(List(1,2,3))(i => List(i,i,i)))

  }

}
