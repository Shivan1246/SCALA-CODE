object CollectionsDemo {

  // Function 1: Squares each element in an integer list
  def squareList(primes: List[Int]): List[Int] = {
    primes.map(n => n * n)
  }

  // Function 2: Computes the intersection of two sets
  def findIntersection[T](setA: Set[T], setB: Set[T]): Set[T] = {
    setA.intersect(setB) // or setA & setB
  }

  def main(args: Array[String]): Unit = {
    // 1. First five prime numbers and squaring them
    val primes: List[Int] = List(2, 3, 5, 7, 11)
    val squaredPrimes = squareList(primes)
    println(s"Original Primes: $primes")
    println(s"Squared Primes:  $squaredPrimes")

    // 2. Set from string and set intersection
    val helloSet: Set[Char] = "hello world".toSet
    val vowelSet: Set[Char] = Set('a', 'e', 'i', 'o', 'u')
    val commonChars = findIntersection(helloSet, vowelSet)
    println(s"\nUnique characters in 'hello world': $helloSet")
    println(s"Intersection with vowels: $commonChars")

    // 3. 3-element Tuple creation and element access
    val mixedTuple: (Int, String, Boolean) = (42, "Scala", true)
    
    val intVal: Int       = mixedTuple._1
    val strVal: String    = mixedTuple._2
    val boolVal: Boolean  = mixedTuple._3

    println(s"\nTuple: $mixedTuple")
    println(s"Element 1 (Int):     $intVal")
    println(s"Element 2 (String):  $strVal")
    println(s"Element 3 (Boolean): $boolVal")
  }
}