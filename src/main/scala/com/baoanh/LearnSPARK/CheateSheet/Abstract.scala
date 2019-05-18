package com.baoanh.LearnSPARK.CheateSheet

object Abstract {
  def main(args: Array[String]): Unit = {
    // Run abstract class through a class
    val merce = new Car()
    merce.run()
    merce.stop()
    // Cant not run abstract through an object
    // val toyota = new Vehicle()
    // toyota.run()
  }
}

// Using abstract class
abstract class Vehicle {
  // non abstract method
  def run() { 
    println("running ...")
  }
  
  // abstract method
  def stop()
}

class Car extends Vehicle {
  // abstract method need to be declared in class
  def stop() {
    println("skitttttt....")
  }
}