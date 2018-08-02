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

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B = {
    as match {
      case Nil => z
      case Cons(head, tail) => f(head, foldRight(tail, z)(f))
    }
  }

  def map(as: List[Int])(f: Int => Int): List[Int] = {
    as match {
      case Nil => Nil
      case Cons(head, tail) => Cons(f(head), map(tail)(f))
    }
  }

  def filter[A](as: List[A])(f: A => Boolean): List[A] ={
    as match {
      case Nil => Nil
      case Cons(head, tail) => if(f(head)) Cons(head,filter(tail)(f)) else filter(tail)(f)
    }
  }

  def flatMap[A,B](as: List[A])(f: A => List[B]): List[B] = {
    as match {
      case Nil => Nil
      case Cons(head, tail) => append(f(head),flatMap(tail)(f))
      }
    }

  def zipWith[A,B,C](a: List[A], b: List[B])(f: (A,B) => C): List[C] = (a,b) match {
    case (Nil, _) => Nil
    case (_, Nil) => Nil
    case (Cons(h1,t1), Cons(h2,t2)) => Cons(f(h1,h2), zipWith(t1,t2)(f))
  }


  def sum2(ns: List[Int]) = {
    foldRight(ns, 0)((x, y) => x + y)
  }

  def product2(ns: List[Int]) = {
    foldRight(ns, 1)(_ * _)
  }

  def sum3(ns: List[Int]) = {
    foldLeft(ns, 0)((x, y) => x + y)
  }

  def product3(ns: List[Int]) = {
    foldLeft(ns, 1)(_ * _)
  }

  def length[A](as: List[A]): Int = {
    foldRight(as, 0)((_, acc) => acc + 1)
  }

  def foldLeft[A, B](as: List[A], z: B)(f: (B, A) => B): B = {
    as match {
      case Nil => z
      case Cons(head, tail) => foldLeft(tail, f(z, head))(f)
    }
  }

  def length2[A](as: List[A]): Int = foldLeft(as, 0)((acc, _) => acc + 1)

  def reverse[A](as: List[A]): List[A] = foldLeft(as, List[A]())((acc, head) => Cons(head, acc))

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
