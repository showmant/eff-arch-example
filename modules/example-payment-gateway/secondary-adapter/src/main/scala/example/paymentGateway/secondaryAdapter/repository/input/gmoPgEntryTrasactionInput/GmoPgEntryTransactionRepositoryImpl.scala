package example.paymentGateway.secondaryAdapter.repository.input.gmoPgEntryTrasactionInput
import com.google.inject.Inject
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput
import example.paymentGateway.domain.repository.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionRepository
import example.paymentGateway.secondaryAdapter.wrapper.gmo.GmoPaymentGatewayWrapper
import org.atnos.eff.Eff

class GmoPgEntryTransactionRepositoryImpl @Inject()(wrapper: GmoPaymentGatewayWrapper)
  extends GmoPgEntryTransactionRepository {
  override def doEntryTransaction[R](
    gmoPgEntryTransactionInput: GmoPgEntryTransactionInput
  ): Eff[
    R,
    GmoPgEntryTransactionOutput
  ] = {
    wrapper.doEntryTran(gmoPgEntryTransactionInput)
    //こっからEffに変換する必要がある？
    //どうやって？w
    ???
  }
}
