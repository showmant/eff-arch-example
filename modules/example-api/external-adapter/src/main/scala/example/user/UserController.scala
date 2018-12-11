package example.user

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer
import com.google.inject.name.Named
import example.RequestDecoder
import example.akkaHttp.AbstractAkkaHttpController
import example.exampleApi.usecase.user.create.CreateUserUseCase
import example.exampleApi.usecase.user.show.ShowUserUseCase
import example.shared.lib.dddSupport.Error
import example.user.dto.show.ShowUserRequest
import javax.inject.Inject

import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success}

class UserController @Inject()(
  showUserPresenter: ShowUserPresenter,
  showUserUseCase: ShowUserUseCase,
  createUserUseCase: CreateUserUseCase
)(
  override implicit val actorMaterializer: ActorMaterializer,
  @Named("default-app-context") override implicit val ec: ExecutionContext
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
                request <- Future.successful(decode[ShowUserRequest](result))
              } yield request
            } {
              case Success(s) =>
                s match {
                  case Right(v)   => complete(s"${v.name.firstName}")
                  case Left(e) => complete(s"$e")
                }
              case Failure(exception) => ???
            }
          }

        }
      }
    }
  private def decode[A](
    jsonString: String
  )(implicit decoder: RequestDecoder[A]): Either[Error, A] =
    decoder.decode(jsonString)

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
