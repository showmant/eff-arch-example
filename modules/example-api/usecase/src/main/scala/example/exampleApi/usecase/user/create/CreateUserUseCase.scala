package example.exampleApi.usecase.user.create

import org.atnos.eff.Eff

import example.exampleApi.domain.model.user.User
import example.shared.lib.dddSupport.usecase.{ EffPushPort, EffUseCase, UseCaseArgument, UseCaseResult }
import example.shared.lib.dddSupport.Error
import example.shared.lib.eff._

import scala.concurrent.ExecutionContext

abstract class CreateUserUseCase
  extends EffUseCase
  with EffPushPort[CreateUserUseCaseArgs, Error, CreateUserUseCaseResult] {

  def execute[R: _trantask: _idgen: _clockm](
    arg: CreateUserUseCaseArgs
  )(implicit ec: ExecutionContext): Eff[R, CreateUserUseCaseResult]

}

case class CreateUserUseCaseArgs(
  name: Option[String]
) extends UseCaseArgument

case class CreateUserUseCaseResult(user: User) extends UseCaseResult
