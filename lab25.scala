//> using scala 2.12.18
//> using dep io.github.pityka::saddle-core:3.0.1
//> using dep org.typelevel::spire:0.17.0

import org.saddle.{Frame, Index, Vec}
import spire.math.Rational

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths}

object FruitStats extends App {

  // ---- Dataset: monthly price per kg (rows = months, columns = fruits) ----
  val months = Seq("1-Jan", "2-Feb", "3-Mar", "4-Apr", "5-May", "6-Jun")

  // ---- Saddle: build a Frame and set the month labels as the row index ----
  val prices: Frame[String, String, Double] =
    Frame(
      "Apple"  -> Vec(118.0, 120.0, 125.0, 122.0, 130.0, 128.0),
      "Banana" -> Vec(38.0, 40.0, 42.0, 39.0, 41.0, 44.0),
      "Orange" -> Vec(58.0, 60.0, 57.0, 62.0, 65.0, 63.0),
      "Mango"  -> Vec(140.0, 150.0, 160.0, 155.0, 148.0, 152.0)
    ).setRowIndex(Index(months: _*))

  val fruits: Seq[String] = prices.colIx.toSeq

  // ---- Spire: exact mean and variance using Rational numbers ----
  def exact(x: Double): Rational = Rational(BigDecimal(x.toString))

  def meanOf(xs: Seq[Double]): Rational =
    xs.map(exact).foldLeft(Rational.zero)(_ + _) / Rational(xs.size)

  def varianceOf(xs: Seq[Double]): Rational = {
    val m = meanOf(xs)
    xs.map(x => (exact(x) - m) * (exact(x) - m)).foldLeft(Rational.zero)(_ + _) /
      Rational(xs.size - 1) // sample variance
  }

  println(f"${"Fruit"}%-10s ${"Mean"}%10s ${"Variance"}%12s")
  fruits.foreach { fruit =>
    val series = prices.firstCol(fruit).values.toSeq // Saddle: pick one column
    println(f"$fruit%-10s ${meanOf(series).toDouble}%10.3f ${varianceOf(series).toDouble}%12.3f")
  }

  // ---- Vega-Lite (the grammar Vegas wraps): line chart of price trend ----
  val values: String =
    (for {
      fruit <- fruits
      (month, price) <- months.zip(prices.firstCol(fruit).values.toSeq)
    } yield s"""{"Month":"$month","Fruit":"$fruit","Price":$price}""").mkString(",")

  val spec: String =
    s"""{
       |  "$$schema": "https://vega.github.io/schema/vega-lite/v5.json",
       |  "title": "Fruit Price Trends Over Time",
       |  "width": 600, "height": 350,
       |  "data": {"values": [$values]},
       |  "mark": {"type": "line", "point": true},
       |  "encoding": {
       |    "x": {"field": "Month", "type": "ordinal", "title": "Month"},
       |    "y": {"field": "Price", "type": "quantitative", "title": "Price per kg"},
       |    "color": {"field": "Fruit", "type": "nominal"}
       |  }
       |}""".stripMargin

  val html: String =
    """<!DOCTYPE html><html><head><meta charset="utf-8">
      |<script src="https://cdn.jsdelivr.net/npm/vega@5"></script>
      |<script src="https://cdn.jsdelivr.net/npm/vega-lite@5"></script>
      |<script src="https://cdn.jsdelivr.net/npm/vega-embed@6"></script>
      |</head><body><div id="vis"></div>
      |<script>vegaEmbed('#vis', __SPEC__);</script>
      |</body></html>""".stripMargin.replace("__SPEC__", spec)

  // Save as an HTML page and open it in the browser
  val out = Paths.get("fruit_trends.html")
  Files.write(out, html.getBytes(StandardCharsets.UTF_8))
  println(s"Chart saved to ${out.toAbsolutePath}")
  try java.awt.Desktop.getDesktop.browse(out.toUri)
  catch { case _: Throwable => println("Open fruit_trends.html in your browser.") }
}