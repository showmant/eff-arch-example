package example
import cats.data.Validated.{ Invalid, Valid }
import cats.data.{ NonEmptyList, Writer }
import cats.implicits._
import example.akkaHttp.Request
import example.circe.CirceDecoder
import example.user.dto.show.{ Name, ShowUserRequest }
import io.circe.Decoder.Result
import io.circe.{ AccumulatingDecoder, _ }
import io.tabmo.circe.extra.rules.{ IntRules, StringRules }
import io.tabmo.json.rules._

sealed trait RequestDecoder[A <: Request] extends CirceDecoder[A] {
  type ResultWriter[C] = Writer[List[DecodingFailure], Option[C]]
  implicit def toWriter[B](result: Result[B]): ResultWriter[B] = result match {
    case Right(value) => Writer.value(value.some)
    case Left(e)      => Writer(List(e), none)
  }
}

object RequestDecoder {
  implicit object ShowUserRequestDecoder extends RequestDecoder[ShowUserRequest] {
    protected override implicit def decoder: AccumulatingDecoder[ShowUserRequest] =
      AccumulatingDecoder.instance[ShowUserRequest] { c: HCursor =>
        val userId: ResultWriter[String] =
          c.downField("userId").read(StringRules.maxLength(32))
        val firstName: ResultWriter[String] =
          c.downField("name").downField("firstName").read(StringRules.maxLength(32))
        val lastName: ResultWriter[String] =
          c.downField("name").downField("lastName").read(StringRules.maxLength(32))
        val age: ResultWriter[Int] = c.downField("age").read(IntRules.positive())

        (for {
          maybeUserId    <- userId
          maybeFirstName <- firstName
          maybeLastName  <- lastName
          maybeAge       <- age
        } yield
          for {
            userId    <- maybeUserId
            firstName <- maybeFirstName
            lastName  <- maybeLastName
            age       <- maybeAge
          } yield ShowUserRequest(userId, Name(firstName, lastName), age)).run match {
          case (Nil, Some(request)) => Valid(request)
          case (h :: t, _)          => Invalid(NonEmptyList(h, t))
        }

      }
  }

}
