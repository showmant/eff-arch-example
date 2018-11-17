package example.paymentGateway.secondaryAdapter.config
import example.shared.adapter.config.support.ApplicationConfBase

class GmoPgConf extends ApplicationConfBase {

  def siteId: String = getString("gateway.gmo.site-id")
  def sitePass: String = getString("gateway.gmo.site-pass")
  def shopNumber: String = getString("gateway.gmo.shop-number")
  def shopId: String = getString("gateway.gmo.shop-id") + shopNumber
  def shopPass: String = getString("gateway.gmo.shop-pass")

  // 物理・論理モードの指定に使う。ださい。
  val RONRI_MODE   = "0"
  val BUTSURI_MODE = "1"

}
