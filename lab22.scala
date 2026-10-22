// 1. Define the case class
case class Person(name: String, age: Int, city: String)

object PersonOperations {

  // 2. Filter out people older than 30 (keeps people with age <= 30)
  def filterOlderThan30(people: List[Person]): List[Person] = {
    people.filter(_.age <= 30)
  }

  // 3. Group people by their city
  def groupByCity(people: List[Person]): Map[String, List[Person]] = {
    people.groupBy(_.city)
  }

  // 4. Transform each Person into the formatted string
  def formatPeople(people: List[Person]): List[String] = {
    people.map(p => s"${p.name} is ${p.age} years old and lives in ${p.city}")
  }

  // 5. Sort people by age in ascending order
  def sortByAgeAscending(people: List[Person]): List[Person] = {
    people.sortBy(_.age)
  }

  def main(args: Array[String]): Unit = {
    // List of five individuals
    val people: List[Person] = List(
      Person("Alice", 28, "New York"),
      Person("Bob", 35, "San Francisco"),
      Person("Charlie", 24, "New York"),
      Person("Diana", 42, "London"),
      Person("Evan", 30, "San Francisco")
    )

    // Run filter
    val underOrEqual30 = filterOlderThan30(people)
    println("--- People aged 30 or younger ---")
    underOrEqual30.foreach(println)

    // Run grouping
    val grouped = groupByCity(people)
    println("\n--- People grouped by city ---")
    grouped.foreach { case (city, residents) =>
      val names = residents.map(_.name).mkString(", ")
      println(s"$city: $names")
    }

    // Run transformation
    val formattedDescriptions = formatPeople(people)
    println("\n--- Formatted Descriptions ---")
    formattedDescriptions.foreach(println)

    // Run sorting
    val sortedByAge = sortByAgeAscending(people)
    println("\n--- Sorted by Age (Ascending) ---")
    sortedByAge.foreach(p => println(s"${p.name} (${p.age})"))
  }
}