package example.exampleApi.usecase.uuidsample
import example.exampleApi.domain.model.user.{UUID, UserId}
import example.exampleApi.usecase.user.show.ShowUserUseCaseResult
import example.shared.lib.dddSupport.usecase.{EffPushPort, EffUseCase, UseCaseArgument, UseCaseResult}
import example.shared.lib.dddSupport.Error
import org.atnos.eff.Eff
import example.shared.lib.eff._

import scala.concurrent.ExecutionContext

abstract class GetUuidUseCase extends EffUseCase with EffPushPort[GetUuidUseCaseArgs, Error, ShowUserUseCaseResult] {
  def execute[R: _idgen](
                             arg: GetUuidUseCaseArgs
                           )(implicit ec: ExecutionContext): Eff[R, GetUuidUseCaseResult]

}

case class GetUuidUseCaseArgs(
                                userId: UserId
                              ) extends UseCaseArgument

case class GetUuidUseCaseResult(userOpt: Option[UUID]) extends UseCaseResult
