import org.saddle._
import vegas._

object FruitPrices extends App {

  // 1. Dataset
  val months = Array("Jan", "Jan", "Feb", "Feb", "Mar", "Mar",
                     "Apr", "Apr", "May", "May", "Jun", "Jun")
  val fruit  = Array("apple", "banana", "apple", "banana", "apple", "banana",
                     "apple", "banana", "apple", "banana", "apple", "banana")
  val price  = Array(1.20, 0.50, 1.30, 0.55, 1.25, 0.52,
                     1.40, 0.60, 1.55, 0.58, 1.50, 0.62)
  val qty    = Array(100.0, 150.0, 110.0, 140.0, 120.0, 160.0,
                     90.0, 155.0, 80.0, 165.0, 95.0, 170.0)

  // 2. Saddle Frame
  val df: Frame[Int, String, Double] =
    Frame("price" -> Vec(price), "quantity" -> Vec(qty))

  println("Full dataset:")
  println(df)

  // 3. Filter for apples
  val appleRows: Array[Int] =
    fruit.zipWithIndex.collect { case ("apple", i) => i }

  val apples = df.rowAt(appleRows)
  println("Apples only:")
  println(apples)

  // 4. Average apple price
  val priceArr: Array[Double] = apples.firstCol("price").toVec.contents
  val avgPrice = priceArr.sum / priceArr.length
  println(f"Average apple price: $$$avgPrice%.2f")

  // 5. Visualize price trend with Vegas
  val appleMonths = appleRows.map(months)
  val plotData: Seq[Map[String, Any]] =
    appleMonths.zip(priceArr).map { case (m, p) =>
      Map("month" -> m, "price" -> p)
    }.toSeq

  val plot = Vegas("Apple Price Trend")
    .withData(plotData)
    .encodeX("month", Ord)
    .encodeY("price", Quant)
    .mark(Line)

  val html = plot.html.pageHTML()
  java.nio.file.Files.write(
    java.nio.file.Paths.get("apple_price_trend.html"),
    html.getBytes("UTF-8")
  )
  println("Chart saved to apple_price_trend.html")
}