package example.paymentGateway.secondaryAdapter.wrapper.gmo
import com.gmo_pg.g_pay.client.common.JobCode
import com.gmo_pg.g_pay.client.input.EntryTranInput
import com.gmo_pg.g_pay.client.output.EntryTranOutput
import example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput.GmoPgEntryTransactionInput
import example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput.GmoPgEntryTransactionOutput

trait EntryTransactionInputConverter /* extends GMO用の設定Confを読み込む */ {
  //この辺がConfに入っているべき

  val GMO_SITE_ID       = ""
  val GMO_SITE_PASSWORD = ""

  val GMO_SHOP_NUMBER   = ""
  val GMO_SHOP_ID       = "" + GMO_SHOP_NUMBER
  val GMO_SHOP_PASSWORD = ""

  // 物理・論理モードの指定に使う。ださい。
  val RONRI_MODE   = "0"
  val BUTSURI_MODE = "1"

  def convertToGmoPGData(domainModel: GmoPgEntryTransactionInput): EntryTranInput = {
    val input = new EntryTranInput
    input.setShopId(GMO_SHOP_ID)
    input.setShopPass(GMO_SHOP_PASSWORD)
    input.setOrderId(domainModel.orderId)
    input.setJobCd(JobCode.CAPTURE)
    input.setAmount(domainModel.amount)
    //input.setTax()
    input.setTdFlag("0") // 3D セキュア使用フラグ
    input
  }

  def convertToDomainModel(dataModel: EntryTranOutput): GmoPgEntryTransactionOutput = {
    GmoPgEntryTransactionOutput(
      dataModel.getAccessId,
      dataModel.getAccessPass
    )
  }

}
