package example.exampleApi.usecase.uuidsample
import com.google.inject.Inject
import example.exampleApi.domain.model.user.UUID
import org.atnos.eff.Eff

import scala.concurrent.ExecutionContext

class GetUuidUseCaseImpl @Inject()() extends GetUuidUseCase {
  override def execute[R: _root_.example.shared.lib.eff._idgen](
    arg: GetUuidUseCaseArgs
  )(
    implicit ec: ExecutionContext
  ): Eff[R, GetUuidUseCaseResult] =
    for {
      uuid <- UUID.generate[R]
    } yield GetUuidUseCaseResult(Some(uuid))
}
