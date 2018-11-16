package example.paymentGateway.domain.repository.input.gmoPgEntryTransactionInput
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput
import example.shared.lib.dddSupport.adapter.secondary.repository.Repository
import org.atnos.eff.Eff

trait GmoPgEntryTransactionRepository[A, B] extends Repository[A, B]{

  def doEntryTransaction[R](gmoPgEntryTransactionInput: GmoPgEntryTransactionInput): Eff[R, GmoPgEntryTransactionOutput]

}
