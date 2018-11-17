package example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput
import example.shared.lib.dddSupport.domain.Identifier

case class GmoPgEntryTransactionInputId(value: String) extends Identifier[String]

object GmoPgEntryTransactionInputId {

}
