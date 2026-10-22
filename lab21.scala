object ListFunctionalOperations {
  def main(args: Array[String]): Unit = {
    // 1. Create a list of integers from 1 to 10
    val numbers: List[Int] = (1 to 10).toList
    println(s"Original List: $numbers")

    // 2. filter: Keep only even numbers
    val evenNumbers: List[Int] = numbers.filter(_ % 2 == 0)
    println(s"Even Numbers:  $evenNumbers")

    // 3. map: Multiply each element by 3
    val multipliedByThree: List[Int] = numbers.map(_ * 3)
    println(s"Multiplied by 3: $multipliedByThree")

    // 4. flatMap: Create a list of tuples (n, n^2)
    val pairedWithSquares: List[(Int, Int)] = numbers.flatMap(n => List((n, n * n)))
    println(s"Paired with Squares: $pairedWithSquares")

    // 5. foldLeft: Calculate the product of all elements
    val product: Int = numbers.foldLeft(1)((acc, elem) => acc * elem)
    println(s"Product of all elements: $product")
  }
}