package example.paymentGateway.domain.model.input.gmoPgExecTransactionInput
import example.shared.lib.dddSupport.domain.Identifier

case class GmoPgExecTransactionInputId(value: String) extends Identifier[String]

object GmoPgExecTransactionInputId {

}
