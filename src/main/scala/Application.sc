import scala.io.Source

import scala.collection.mutable.ListBuffer

object Application extends App {
  class Country(val name: String, val region: String, val capital: String, val area: Double) {

    override def toString = s"Country($name, $region, $capital, $area)"

    def toJson = new StringBuilder(s"{\n    “name”: $name\n    “capital”: $capital\n    “area”: $area\n  }").toString
  }

  val source = Source.fromURL("https://raw.githubusercontent.com/mledoze/countries/master/countries.json")
  val arr = new ListBuffer[Country]()

  val strings = source.getLines()

  var bufNames = new ListBuffer[String]()
  var bufRegion = new ListBuffer[String]()
  var bufCapital = new ListBuffer[String]()
  var bufArea = new ListBuffer[String]()
  while (strings.hasNext) {
    val s = strings.next()
    if (s.contains("\"name\"")) {
      val ss = strings.next()
      if (ss.contains("common")) {
        bufNames += ss.replaceAll("\"common\": \"", "").replaceAll("\",", "").replaceAll("            ", "") //bufNames.append(ss.replaceAll("\"common\": \"","").replaceAll("\"",""))
      }
    }
    if (s.contains("\"region\"")) {
      bufRegion += s.replaceFirst("\"region\": \"", "").replaceAll("\",", "").replaceAll("        ", "")
    }
    if (s.contains("capital")) {
      val ss = strings.next()
      bufCapital += ss.replaceFirst("\"", "").replaceAll("\"", "").replaceAll("            ", "")
    }
    if (s.contains("\"area\"")) {
      bufArea += s.replaceAll("\"area\":", "").replaceAll(",", "").replaceAll("         ", "")
    }
  }

  var ri = bufRegion.zipWithIndex
  var ci = bufCapital.zipWithIndex
  var ai = bufArea.zipWithIndex

  bufNames.zipWithIndex.foreach(ni => {
    val cuntry = new Country(ni._1, ri(ni._2)._1, ci(ni._2)._1, ai(ni._2)._1.toDouble)
    arr += cuntry
  })

  var arrAfric = arr.filter(c => {
    c.region == "Africa"
  }).sortWith((c1, c2) => {
    c1.area > c2.area
  })


  var json = new StringBuffer("[")

  arrAfric.remove(10, arrAfric.size - 10)

  arrAfric.foreach(c => {
    json.append(c.toJson)
    json.append("\n")
  })
  json.append("]")

  json.toString()
}


