package com.baoanh.LearnSPARK.CheateSheet

object PAF {
  /**
   * Partially-Applied Function:
   * is a function take multiple parameters and return a function with fewer parameters
   */
  /* Example
   * function add: to add 2 number
   */
  def add(x: Int, y: Int) = x + y
  val addCurried = (add _).curried //=> return add(x: Int)(y: Int)
  val add2 = add(2, _: Int)
  /*
   * Example:
   * Function tag in html
   */
  def tagHtml(start: String, text: String, end: String) = start + text + end
  /*using curried method*/
  val divTagcurried = (tagHtml _).curried // curried method work with function declared, not anonymous function
  val divTag = divTagcurried("<div>")(_: String)("</div>")
  val tagHtml2 = (start: String, text: String, end: String) => start + text + end
  val titleTag = tagHtml2("<title>", _: String, "</title>")
  def main(args: Array[String]) = {
    /*
     * Example 1:
     */
    println(add(3,4))
    val curried2 = addCurried(1)(_)
    println(curried2(6))
    print(add2(5))
    println()
    /*
     * Example 2:
     */
    println(divTag("Hello world"))
    println(titleTag("Learning Scala"))
  }
}