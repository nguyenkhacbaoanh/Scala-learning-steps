package com.baoanh.LearnSPARK.CheateSheet

object MapFmapFlattenFilter {
  def main(args: Array[String]) = {
    // map method
    val myMap = Map(1-> "Anh", 2 -> "Ngoc", 3 -> "Jerome")
    println(myMap.mapValues(x => "Hello " + x)) //return map with Hello + original value
    // map method return the same length of origin map with the modified value or key
    // -----------
    
    // flatten method just return 1D array
    val myList = List(List(1,5,8,3), List(9,14,2))
    println(f"original list: $myList")
    println(f"flatten list: ${myList.flatten}")
    val myList1 = List(List(1,5,8,3), List(9,14,2), List(List(68,5,12), List(1,86)))
    println(f"original list: $myList1")
    println(f"flatten list: ${myList1.flatten}")
    // ------------
    
    // flatMap
    val myFlatMap = myList.flatten.flatMap(x => List(x, x + 1))
    print(f"My flatMap after origin list ${myList.flatten} \nis ${myFlatMap}")
    // flatMap take an apply function to each element and return result of that treatment
    // -------------
    
    // filter method
    print(f"My FlatMap ${myList.flatten} filtered is: \n${myList.flatten.filter(_ % 2 == 0)}")
    // return result that satisfy a condition, ex: all of even number like above 
  }
}