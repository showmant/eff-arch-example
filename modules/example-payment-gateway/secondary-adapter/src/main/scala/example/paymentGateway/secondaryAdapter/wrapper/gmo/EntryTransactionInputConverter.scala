package example.paymentGateway.secondaryAdapter.wrapper.gmo
import com.gmo_pg.g_pay.client.common.JobCode
import com.gmo_pg.g_pay.client.input.EntryTranInput
import com.gmo_pg.g_pay.client.output.EntryTranOutput
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput
import example.paymentGateway.secondaryAdapter.config.GmoPgConf

protected trait EntryTransactionInputConverter  {

  val config: GmoPgConf


  def convertToGmoPGData(domainModel: GmoPgEntryTransactionInput): EntryTranInput = {
    val input = new EntryTranInput
    input.setShopId(config.shopId)
    input.setShopPass(config.shopPass)
    input.setOrderId(domainModel.orderId)
    input.setJobCd(JobCode.CAPTURE)
    input.setAmount(domainModel.amount)
    //input.setTax()
    input.setTdFlag("0") // 3D セキュア使用フラグ
    input
  }

  def convertToDomainModel(dataModel: EntryTranOutput): GmoPgEntryTransactionOutput = {
    GmoPgEntryTransactionOutput(
      ???,
      dataModel.getAccessId,
      dataModel.getAccessPass
    )
  }

}
