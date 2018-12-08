package example.user

import akka.actor.ActorSystem
import akka.http.scaladsl.model.{ ContentTypes, HttpEntity }
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{ Route, StandardRoute }
import akka.stream.ActorMaterializer
import cats.data.Validated.{ Invalid, Valid }
import cats.data.ValidatedNel
import example.RequestDecoder
import example.akkaHttp.{ AbstractAkkaHttpController, Request }
import example.exampleApi.usecase.user.create.CreateUserUseCase
import example.exampleApi.usecase.user.show.ShowUserUseCase
import example.user.dto.show.ShowUserRequest
import javax.inject.Inject

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

class UserController @Inject()(
  showUserPresenter: ShowUserPresenter,
  showUserUseCase: ShowUserUseCase,
  createUserUseCase: CreateUserUseCase
)(
  override implicit val actorMaterializer: ActorMaterializer,
  override implicit val ec: ExecutionContext = ExecutionContext.global
) extends AbstractAkkaHttpController {

  override def routes: Route =
    path("users") {
      pathEndOrSingleSlash {
        get {
          // GET $host/users
          index()
        }
      } ~
//      post {
//        // POST $host/users
//        entity(as[CreateUserRequest]) { request =>
//          create(request.name)
//        }
//      } ~
      path(".*".r) { userId: String =>
        get {
          // GET $host/users/${userId}
          show(userId)
        }
      }
    } ~
    path("person") {
      pathEndOrSingleSlash {
        post {
          extract(ctx => ctx.request) { request =>
            onComplete {
              for {
                result  <- extractRequest(request)
                request <- Future.successful(decode2[ShowUserRequest](result))
              } yield request
            } {
              case Success(s) =>
                s match {
                  case Valid(v)   => complete(s"${v.name.firstName}")
                  case Invalid(e) => complete(s"${e.size}")
                }
              case Failure(exception) => ???
            }
          }

        }
      }
    }
  private def decode2[A <: Request](
    jsonString: String
  )(implicit decoder: RequestDecoder[A]): ValidatedNel[io.circe.Error, A] =
    decoder.decode2(jsonString)

  private def index(): StandardRoute = ???

  private def show(userId: String): Route = ???
//    {
//    type R = FxAppend[DBStack, Fx.fx1[ErrorEither]]
//    (for {
//      useCaseRes <- {
//        val arg = ShowUserUseCaseArgs(UserId(userId))
//        showUserUseCase.execute[R](arg)
//      }
//    } yield useCaseRes)
//  }

//    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>SHOW USER</h1>"))
//  }

  private def create(name: Option[String]): Route = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>CREATE USER</h1>"))
  }

  private def err: StandardRoute = failWith(sys.error("aaa"))
}
