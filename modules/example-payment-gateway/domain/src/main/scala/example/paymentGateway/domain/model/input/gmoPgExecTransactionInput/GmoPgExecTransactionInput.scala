package example.paymentGateway.domain.model.input.gmoPgExecTransactionInput

case class GmoPgExecTransactionInput(
  inputId: GmoPgExecTransactionInputId,
  accessId: String,
  accessPass: String,
  orderId: String,
  token: String,
  memberId: String)
