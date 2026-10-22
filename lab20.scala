object StudentGradeMaps {

  // 1. Merge two Maps, taking the maximum grade on key collisions
  def mergeWithHigherGrade(
      map1: Map[String, Int],
      map2: Map[String, Int]
  ): Map[String, Int] = {
    // Collect all unique keys across both maps
    val allKeys = map1.keySet ++ map2.keySet
    allKeys.map { key =>
      val grade1 = map1.get(key)
      val grade2 = map2.get(key)
      val bestGrade = (grade1, grade2) match {
        case (Some(g1), Some(g2)) => math.max(g1, g2)
        case (Some(g1), None)     => g1
        case (None, Some(g2))     => g2
        case (None, None)         => 0 // Unreachable
      }
      key -> bestGrade
    }.toMap
  }

  // 2. Resolve collisions by chaining: accumulate all conflicting values into a List
  def mergeWithChaining(
      entries: Seq[(String, Int)]
  ): Map[String, List[Int]] = {
    entries.foldLeft(Map.empty[String, List[Int]]) { (acc, entry) =>
      val (student, grade) = entry
      val existingGrades = acc.getOrElse(student, List.empty[Int])
      acc + (student -> (existingGrades :+ grade))
    }
  }

  def main(args: Array[String]): Unit = {
    // --- Step 1: Basic HashMap Insertion & Duplicate Key Demonstration ---
    println("=== 1. Standard Map Insertion with Duplicate Key ===")
    
    // Inserting pairs sequentially; "Alice" is added twice with different grades
    val initialGrades = Map(
      "Alice" -> 82,
      "Bob"   -> 74,
      "Carol" -> 91
    )
    // Inserting duplicate key "Alice" with a new grade
    val updatedGrades = initialGrades + ("Alice" -> 95)

    println(s"Initial Map: $initialGrades")
    println(s"After inserting ('Alice' -> 95): $updatedGrades")
    println("Observation: The original grade (82) was overwritten by 95.\n")

    // --- Step 2: Merge Two HashMaps with Conflict Resolution (Max Grade) ---
    println("=== 2. Merge Two Maps Resolving Conflicts by Higher Grade ===")
    
    val midtermGrades = Map(
      "Alice" -> 88,
      "Bob"   -> 79,
      "David" -> 92
    )

    val finalExamGrades = Map(
      "Alice" -> 94, // Collision: 94 > 88 -> resolves to 94
      "Bob"   -> 72, // Collision: 79 > 72 -> resolves to 79
      "Carol" -> 85  // New key
    )

    val bestGrades = mergeWithHigherGrade(midtermGrades, finalExamGrades)
    println(s"Midterm Map:    $midtermGrades")
    println(s"Final Exam Map: $finalExamGrades")
    println(s"Merged (Max):   $bestGrades\n")

    // --- Step 3: Collision Handling via Chaining (Lists of Values) ---
    println("=== 3. Collision Handling via Chaining (Lists of Grades) ===")
    
    // A sequence of grade submissions that contains repeating students
    val submissionLog = Seq(
      "Alice" -> 85,
      "Bob"   -> 78,
      "Alice" -> 92,
      "Carol" -> 90,
      "Alice" -> 95,
      "Bob"   -> 84
    )

    val chainedGrades = mergeWithChaining(submissionLog)
    println("Input Submissions:")
    submissionLog.foreach { case (name, grade) => println(s"  $name -> $grade") }
    
    println("\nChained Map Result:")
    chainedGrades.foreach { case (student, grades) =>
      println(s"  $student -> ${grades.mkString("[", ", ", "]")}")
    }
  }
} 
