package example.paymentGateway.domain.model.input.gmoPgExecTransactionInput

case class GmoPgExecTransactionInput(
  accessId: String,
  accessPass: String,
  orderId: String,
  token: String,
  memberId: String)
