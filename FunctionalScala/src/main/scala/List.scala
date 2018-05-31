sealed trait List[+A]

case object Nil extends List[Nothing]

case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {

  def sum(ints: List[Int]): Int = {
    ints match {
      case Nil => 0
      case Cons(x, xs) => x + sum(xs)
    }
  }

  def product(ints: List[Int]): Int = {
    ints match {
      case Nil => 1
      case Cons(0, _) => 0
      case Cons(x, xs) => x * product(xs)
    }
  }

  def apply[A](as: A*): List[A] = {
    // Variadic function syntax
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
  }

  def tail[A](ints: List[A]): List[A] = {
    ints match {
      case Nil => Nil
      case Cons(_, Cons(y, z)) => Cons(y, z)
      case Cons(_, Nil) => Nil
    }
  }

  def setHead[A](ints: List[A], head: A): List[A] = {
    ints match {
      case Nil => Nil
      case Cons(_, Cons(y, z)) => Cons(head, Cons(y, z))
      case Cons(_, Nil) => Cons(head, Nil)
    }
  }

  def drop[A](l: List[A], n: Int): List[A] = {
    n match {
      case 0 => l
      case _ => drop(tail(l), n - 1)
    }
  }

  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = {
    l match {
      case Nil => Nil
      case Cons(h, t) => if (f(h)) dropWhile(t, f) else l
    }
  }

  def append[A](a1: List[A], a2: List[A]): List[A] = {
    a1 match {
      case Nil => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }
  }

  def init[A](l: List[A]): List[A] =
    l match {
      case Nil => sys.error("init of empty list")
      case Cons(_, Nil) => Nil
      case Cons(h, t) => Cons(h, init(t))
    }

//  def init2[A](l: List[A]): List[A] = {
//    val buf = new ListBuffer[A]
//
//    @annotation.tailrec
//    def go(cur: List[A]): List[A] = cur match {
//      case Nil => sys.error("init of empty list")
//      case Cons(_, Nil) => List(buf.toList: _*)
//      case Cons(h, t) => buf += h; go(t)
//    }
//
//    go(l)
//  }
}
