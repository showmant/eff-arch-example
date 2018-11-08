package example.controller

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.google.inject.Inject
import example.exampleApi.domain.model.user.{UUID, UserId}
import example.exampleApi.usecase.uuidsample.{GetUuidUseCase, GetUuidUseCaseArgs}
import example.shared.lib.eff._
import example.shared.lib.eff.util.idGen.IdGen
import example.shared.lib.eff.util.idGen.interpreter.IdGenInterpreter
import org.atnos.eff.Fx
import org.atnos.eff.syntax.eff._

import scala.concurrent.ExecutionContext

class UserController @Inject()(
                                getUuidUseCase: GetUuidUseCase)(
  implicit idGenInterpreter: IdGenInterpreter) {

  def show[R: _trantask](): Route = {
    implicit val ec = ExecutionContext.global
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>SHOW USER</h1>"))
  }

  type R = Fx.fx1[IdGen]
  def getUUID(): UUID = {
    implicit val ec = ExecutionContext.global
    val a = (for {
      uuid <- getUuidUseCase.execute[R](GetUuidUseCaseArgs(UserId("1")))

    } yield uuid).runIdGen.runPure

    a match {
      case Some(result) => result.userOpt.get
      case None         => ???
    }

  }

}
