package example.paymentGateway.secondaryAdapter.wrapper.gmo
import com.gmo_pg.g_pay.client.common.Method
import com.gmo_pg.g_pay.client.input.ExecTranInput
import com.gmo_pg.g_pay.client.output.ExecTranOutput
import example.paymentGateway.domain.model.input.gmoPgExecTransactionInput.GmoPgExecTransactionInput
import example.paymentGateway.domain.model.output.gmoPgExecTransactionOutput.GmoPgExecTransactionOutput
import example.paymentGateway.secondaryAdapter.config.GmoPgConf

protected trait ExecTransactionInputConverter {

  val config: GmoPgConf

  def convertToGmoPGData(domainModel: GmoPgExecTransactionInput): ExecTranInput = {
    val input = new ExecTranInput
    input.setAccessId(domainModel.accessId)
    input.setAccessPass(domainModel.accessPass)
    input.setOrderId(domainModel.orderId)
    input.setMethod(Method.IKKATU)
    input.setToken(domainModel.token)
    input.setMemberId(domainModel.memberId)
    input
  }

  def convertToDomainModel(dataModel: ExecTranOutput): GmoPgExecTransactionOutput = {
    GmoPgExecTransactionOutput(
      //IDはどうやってふろうか。。。。
      ???
    )
  }

}
