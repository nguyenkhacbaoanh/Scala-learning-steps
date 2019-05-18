package com.baoanh.LearnSPARK.CheateSheet

object scala {
  val vector = (1 to 50)
//  filter by even and multiple by 2
  println(vector filter {
    _ % 2 == 0
  } map {
    _ * 2
  })
}