package com.baoanh.LearnSPARK.CheateSheet

object ListVal {
  import scala.io.StdIn._
  import scala.collection.mutable.ListBuffer
  // List type in scala is immutable, there why we need to import ListBuffer in mutable class
  import util.control.Breaks._
  def main(args: Array[String]) = {
    val result : ListBuffer[String] = ListBuffer[String]()
    println("Enter your number: ")
    breakable {
      while (true) {
        val r = readLine()
        result.+=(r) // += to add in the end if List, +=: to add in front of the List
        println(result)
        if (r == "" || r == None) break
      }
    }
    
    // println(r :: result)
  }
}