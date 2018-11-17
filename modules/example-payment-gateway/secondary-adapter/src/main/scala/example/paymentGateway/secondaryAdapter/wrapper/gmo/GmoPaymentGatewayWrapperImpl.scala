package example.paymentGateway.secondaryAdapter.wrapper.gmo
import cats.data.Writer
import com.gmo_pg.g_pay.client.PaymentClient
import com.gmo_pg.g_pay.client.output._
import com.google.inject.Inject
import example.paymentGateway.domain.model.error.gmoError.GmoError
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.input.gmoPgExecTransactionInput.GmoPgExecTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput
import example.paymentGateway.domain.model.output.gmoPgExecTransactionOutput.GmoPgExecTransactionOutput
import example.paymentGateway.secondaryAdapter.config.GmoPgConf
import example.shared.lib.dddSupport.Error.ThirdPartyServiceError
import example.shared.lib.dddSupport.ErrorCode
import monix.eval.Task

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext
import scala.util.control.NonFatal
import scala.util.{ Failure, Success, Try }

class GmoPaymentGatewayWrapperImpl @Inject()(client: PaymentClient, override val config: GmoPgConf)(
  implicit val ec: ExecutionContext
) extends GmoPaymentGatewayWrapper
  with EntryTransactionInputConverter
  with ExecTransactionInputConverter {

  override def doEntryTran(
    domainModel: GmoPgEntryTransactionInput
  ): Task[Either[GmoErrorWriter, GmoPgEntryTransactionOutput]] =
    Task.now {
      val input = convertToGmoPGData(domainModel)
      Try(client.doEntryTran(input)) match {
        case Success(op) if !op.isErrorOccurred => Right(convertToDomainModel(op))
        case fail                               => Left(Writer.tell(handleError(fail)))
      }
    }

  override def doExecTran(
    domainModel: GmoPgExecTransactionInput
  ): Task[Either[GmoErrorWriter, GmoPgExecTransactionOutput]] =
    Task.now {
      val input = convertToGmoPGData(domainModel)
      Try(client.doExecTran(input)) match {
        case Success(op) if !op.isErrorOccurred => Right(convertToDomainModel(op))
        case fail                               => Left(Writer.tell(handleError(fail)))
      }

    }

  private[this] def handleError(_try: Try[BaseOutput]): List[ThirdPartyServiceError] =
    _try match {
      case Success(output) => createThirdPartyErrorsFrom(output)
      case Failure(e)      => List(handleFailure(e))
    }

  private[this] def handleFailure(e: Throwable): ThirdPartyServiceError =
    e match {
      case NonFatal(error) => createThirdPartyError(error)
      case _               => throw InternalError
    }

  private[this] def createThirdPartyErrorsFrom(output: BaseOutput): List[ThirdPartyServiceError] = {

    def anysToErrors(anys: List[Any]): List[ThirdPartyServiceError] = {
      for {
        any <- anys
        errHolder = any.asInstanceOf[ErrHolder]
      } yield createThirdPartyError(GmoError(errHolder.getErrInfo))
    }
    anysToErrors(output.getErrList.asScala.toList)

  }

  private[this] def createThirdPartyError(err: Throwable) = ThirdPartyServiceError(err, ErrorCode.GMO_PG_ERROR)

}
