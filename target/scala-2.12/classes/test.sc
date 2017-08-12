import io.circe.Decoder
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import io.circe.parser._
import io.circe.Json

val doc: String = """
            |{"list": [{
            |			"id": 31734,
            |			"surfaceSqft": 2700.0
            |		}]}
""".stripMargin

case class Clip(surfaceSqft: BigDecimal)

val x = parse(doc)  match {
  case Left(failure) => failure
  case Right(json) => {
    json.hcursor.downField("list").as[List[Clip]]
  }
}

x
//val cursor = x.hcursor
//cursor.downField("id").as[Int]
//
//case class Commit(surfaceSqft: BigDecimal)
//
//object Commit {
//
//  implicit val decodeCommit: Decoder[Commit] = for {
//    surfaceSqft <- Decoder.instance(c => c.downField("surfaceSqft"))
//  } yield Commit(surfaceSqft)
//}
//
//decode[Commit](doc)