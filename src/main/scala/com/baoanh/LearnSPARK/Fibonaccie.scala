package com.baoanh.LearnSPARK
import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer

//object Fibonaccie {
//  def Fibo(x: Int): BigInt = {
//    @tailrec
//    def fibHelp(x:Int, prev:BigInt=0, next:BigInt=1): BigInt = x match {
//      case 0 => prev
//      case 1 => next
//      case _ => {
//        println(x)
//        fibHelp(x - 1, prev, (next+prev))
//      }
//    }
//    fibHelp(x)
//  }
//  def main(args: Array[String]):Unit = {
//    println(Fibo(10))
//  }
//}

/**
 * Fibonacci using recursive function
 */
object Fibonaccie {
    def main(args: Array[String]): Unit = {
      // chain of fibonacci of 10
      println(fib(10))
      // Test map function
      fib(10).map(_*2).foreach(println)
    }

    def fib(x: Int): ListBuffer[BigInt] = {
      var result = new ListBuffer[BigInt]()
        @tailrec def fib_recur(x: Int, prev:BigInt, current:BigInt): BigInt = {
//            println("Index %d".format(x))
//            println("Prev %d".format(prev))
            result += prev
//            println("Current %d".format(current))
            if (x <= 2) {
              current
            } else {
              fib_recur(x - 1, prev = current, current = prev + current)
            }
        }
      fib_recur(x+1, prev=0, current=1);
      return result
    }

}