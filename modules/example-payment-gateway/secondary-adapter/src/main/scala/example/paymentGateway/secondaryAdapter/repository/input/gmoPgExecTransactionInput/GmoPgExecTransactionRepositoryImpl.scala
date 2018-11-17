package example.paymentGateway.secondaryAdapter.repository.input.gmoPgExecTransactionInput

import com.google.inject.Inject
import example.paymentGateway.domain.model.input.gmoPgExecTransactionInput.GmoPgExecTransactionInput
import example.paymentGateway.domain.model.output.gmoPgExecTransactionOutput.GmoPgExecTransactionOutput
import example.paymentGateway.domain.repository.input.gmoPgExecTransactionInput.GmoPgExecTransactionRepository
import example.paymentGateway.secondaryAdapter.wrapper.gmo.GmoPaymentGatewayWrapper
import org.atnos.eff.Eff

class GmoPgExecTransactionRepositoryImpl @Inject()(wrapper: GmoPaymentGatewayWrapper)
  extends GmoPgExecTransactionRepository {
  override def doExecTransaction[R](
    gmoPgExecTransactionInput: GmoPgExecTransactionInput
  ): Eff[
    R,
    GmoPgExecTransactionOutput
  ] = {
    wrapper.doExecTran(gmoPgExecTransactionInput)
    //こっからEffに変換する必要がある？
    //どうやって？w
    ???
  }
}
