package example.paymentGateway.secondaryAdapter.wrapper.gmo
import com.gmo_pg.g_pay.client.common.Method
import com.gmo_pg.g_pay.client.input.ExecTranInput
import com.gmo_pg.g_pay.client.output.ExecTranOutput
import example.paymentGateway.domain.model.input.gmoPgExecTransactionInput.GmoPgExecTransactionInput
import example.paymentGateway.domain.model.output.gmoPgExecTransactionOutput.GmoPgExecTransactionOutput

trait ExecTransactionInputConverter {
  //この辺がConfに入っているべき

  val GMO_SITE_ID       = ""
  val GMO_SITE_PASSWORD = ""

  val GMO_SHOP_NUMBER   = ""
  val GMO_SHOP_ID       = "" + GMO_SHOP_NUMBER
  val GMO_SHOP_PASSWORD = ""

  // 物理・論理モードの指定に使う。ださい。
  val RONRI_MODE   = "0"
  val BUTSURI_MODE = "1"

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
    )
  }

}
