package example.paymentGateway.domain.repository.input.gmoPgExecTransactionInput
import example.paymentGateway.domain.model.input.gmoPgExecTransactionInput.{GmoPgExecTransactionInput, GmoPgExecTransactionInputId}
import example.paymentGateway.domain.model.output.gmoPgExecTransactionOutput.GmoPgExecTransactionOutput
import example.shared.lib.dddSupport.adapter.secondary.repository.Repository
import org.atnos.eff.Eff

trait GmoPgExecTransactionRepository extends Repository[GmoPgExecTransactionInputId, GmoPgExecTransactionInput] {
  def doExecTransaction[R](gmoPgEntryTransactionInput: GmoPgExecTransactionInput): Eff[R, GmoPgExecTransactionOutput]
}
