//> using scala 2.13.12
//> using dep org.scalanlp::breeze:2.1.0
//> using dep org.plotly-scala::plotly-render:0.8.5

import breeze.linalg.{DenseVector, sum}
import plotly._
import plotly.element._
import plotly.layout._

object FruitRevenue extends App {

  // ---- Dataset ----
  val fruits   = Seq("Apple", "Banana", "Orange", "Mango", "Grapes", "Pineapple")
  val price    = DenseVector(120.0, 40.0, 60.0, 150.0, 90.0, 70.0)   // price per kg
  val quantity = DenseVector(50.0, 120.0, 80.0, 30.0, 60.0, 45.0)    // kg sold

  // ---- Breeze: element-wise multiplication ----
  val revenue: DenseVector[Double] = price *:* quantity

  println("Fruit        Price   Qty   Revenue")
  fruits.indices.foreach { i =>
    println(f"${fruits(i)}%-12s ${price(i)}%6.1f ${quantity(i)}%5.0f ${revenue(i)}%9.1f")
  }
  println(f"Total revenue: ${sum(revenue)}%.1f")

  // ---- Plotly: bar chart ----
  val bar = Bar(fruits, revenue.toArray.toSeq).withName("Revenue")

  val layout = Layout()
    .withTitle("Total Revenue by Fruit")
    .withXaxis(Axis().withTitle("Fruit"))
    .withYaxis(Axis().withTitle("Revenue"))

  Plotly.plot(
    path = "fruit_revenue.html",
    traces = Seq(bar),
    layout = layout,
    openInBrowser = true,
    addSuffixIfExists = false
  )
}