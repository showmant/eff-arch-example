package example.paymentGateway.secondaryAdapter.repository.input.gmoPgEntryTrasactionInput
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput
import example.paymentGateway.domain.repository.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionRepository
import org.atnos.eff.Eff

class GmoPgEntryTransactionRepositoryImpl[A, B] extends GmoPgEntryTransactionRepository[A, B] {
  override def doEntryTransaction[R](
    gmoPgEntryTransactionInput: GmoPgEntryTransactionInput
  ): Eff[
    R,
    GmoPgEntryTransactionOutput
  ] = ???
  override type This = this.type
}
