package example.paymentGateway.secondaryAdapter.wrapper.gmo
import cats.data.Writer
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.input.gmoPgExecTransactionInput.GmoPgExecTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput
import example.paymentGateway.domain.model.output.gmoPgExecTransactionOutput.GmoPgExecTransactionOutput
import monix.eval.Task
import example.shared.lib.dddSupport.Error.ThirdPartyServiceError

abstract class GmoPaymentGatewayWrapper {
  type GmoErrorWriter = Writer[List[ThirdPartyServiceError], Unit]
  def doEntryTran(domainModel: GmoPgEntryTransactionInput): Task[Either[GmoErrorWriter, GmoPgEntryTransactionOutput]]
  def doExecTran(domainModel: GmoPgExecTransactionInput): Task[Either[GmoErrorWriter, GmoPgExecTransactionOutput]]
}
