package com.baoanh.LearnSPARK.CheateSheet

object CurryFunction {
  /**
   * Curring is a technique of transforming a function that take
   * multiple parameters into a function that takes a single argument  
   */
  /*
   * This scripts exlains why we used curried method in scripts `PAF`
   * and how declare curring function
   * There are 3 ways to declare curring function: one ways said in PAF
   */
  /*1*/
  def add(x: Int) = (y: Int) => x + y
  
  /*2*/
  def add2(x: Int) (y: Int) = x + y //declare follow this ways, when your instance need to use wildcard (_)
  
  def main(args: Array[String]) = {
    val sum20 = add(20)
    val sum50 = sum20(30)
    println(sum50)
    
    val sum30 = add2(30)_
    val sum100 = sum30(70)
    println(sum100)
  }
}