package example.qiita

import java.util.Date

import akka.http.scaladsl.model.HttpRequest
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.util.ByteString
import cats.data.Validated.{ Invalid, Valid }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

case class FormError(errors: List[io.circe.Error]) extends Throwable

class PersonController() {
  implicit val actorMaterializer: ActorMaterializer = ???
  implicit val ec: ExecutionContext                 = ???

  def routes: Route =
    path("person") {
      pathEndOrSingleSlash {
        post {
          extract(ctx => ctx.request) { request =>
            onComplete {
              for {
                result <- extractRequest(request)
              } yield result
            } {
              case Success(s) =>
                s match {
                  case Right(person) => complete(person.firstName)
                  case Left(error)   => failWith(error)
                }
              case Failure(exception) => failWith(exception)
            }
          }

        }
      }
    }
  def extractRequest(request: HttpRequest): Future[Either[FormError, Person]] =
    request.entity.dataBytes
      .runFold(ByteString.empty)(_ ++ _)
      .map(_.utf8String)
      .map(jsonToPerson)

  def jsonToPerson(stringJson: String): Either[FormError, Person] = {
    implicit def decoder: io.circe.Decoder[Person] = ???

    io.circe.parser.decodeAccumulating(stringJson) match {
      case Valid(value)    => Right(value)
      case Invalid(errors) => Left(FormError(errors.toList))

    }
  }

}
