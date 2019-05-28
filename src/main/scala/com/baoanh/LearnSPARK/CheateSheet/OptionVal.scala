package com.baoanh.LearnSPARK.CheateSheet

import scala.io.StdIn.readLine

object OptionVal {
  /**
   * Option type used in case for finding elements
   * ex: find method of Array class return Option type
   * if it is found: return type Some
   * return None if not
   */
  def main(args: Array[String]) = {
    val a = Array(3,1,5,8,2,9,3,4,1)
    val found9 = a.find(_ == 9)
    println(found9)
    println(s"Found ${found9.get} in Array")
    val notfound = a.find(_ > 10)
    println(s"Found elements < 10 $notfound")
    println(s"Found elements < 10 ${notfound.getOrElse(0)}")
    /*
     * Option used in case for using the method map, filter, match, foreach
     */
    /*
     * Ex on documentation scala
     * 
     */
    println("---------")
    val input = readLine() //return String
    // Convert
    val name: Option[String] = {
      if (input == null) None else Some(input)
    }
    val upper = name map { _.trim } filter { _.length != 0 } map { _.toUpperCase }
    println(upper getOrElse "")
    
  }
}