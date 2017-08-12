import scalaj.http.{Http, HttpRequest, HttpOptions, HttpResponse}
import io.circe.generic.auto._, io.circe.parser._

object AverageSurfaceCalculator extends App {

  val url: String = "http://search.property.works/properties/search?location=brighton&limit=50"

  val httpRequest: HttpRequest =
    Http(url)
      .option(HttpOptions.connTimeout(10000))
      .option(HttpOptions.readTimeout(50000))

  val httpResponse: HttpResponse[String] = httpRequest.asString
  val httpBody: String = httpResponse.body

  val dataList: List[Data] = parse(httpBody) match {
    case Left(failure) => List.empty
    case Right(json) => json.hcursor.downField("data").as[List[Data]].getOrElse(List.empty)
  }

  val foldDataList: (Double, Int) = dataList.foldLeft((0.0, 0)){
     case((total: Double, numberOfElements: Int), Data(surfaceSqft)) => {
        surfaceSqft.map {
          value: Double => (total + value, numberOfElements + 1)
        }.getOrElse((total, numberOfElements))
     }
   }

  val (total, numberOfElements) = foldDataList
  val averageSurface = total/numberOfElements

  println(averageSurface)
}

case class Data(surfaceSqft: Option[Double])