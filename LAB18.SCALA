object ArrayOperations {
  // 1. Function to sum an integer array
  def sumArray(numbers: Array[Int]): Int = {
    numbers.sum
    // Alternatively without built-in: numbers.foldLeft(0)(_ + _)
  }

  // 2. Function to concatenate string array elements separated by commas
  def concatenateFruits(fruits: Array[String]): String = {
    fruits.mkString(", ")
  }

  def main(args: Array[String]): Unit = {
    // Array of integers
    val numbers: Array[Int] = Array(2, 5, 9, 14, 20)
    val totalSum = sumArray(numbers)
    println(s"Sum of integers: $totalSum")

    // Array of strings
    val fruits: Array[String] = Array("Apple", "Banana", "Orange", "Mango", "Strawberry")
    val fruitListString = concatenateFruits(fruits)
    println(s"Fruits: $fruitListString")

    // Iterating over the integer array and printing each element
    println("\nInteger elements:")
    numbers.foreach(println)
  }
}