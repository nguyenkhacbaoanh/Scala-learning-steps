package com.baoanh.LearnSPARK.CheateSheet

object HigherOrderFunction {
  // Higher Order Function
  /**
   * This is a function take a function as a parameter and return a function
   */
  def math(x: Int, y: Int, f: (Int,Int) => Int): Int = {
    f(x,y)
  }
  
  // run test function
  def main(args: Array[String]) = {
//    anonymous function
    val f = (x: Int, y: Int) => x * y
//    println(f(10,20))
    println(math(3,4, (x,y)=>x*y))
//    using anonymous function
    println(math(3,4, f))
//    using wild card
    println(math(3,4, _*_))
  }
}