package example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput

sealed trait GmoPgInput

case class GmoPgEntryTransactionInput(
  inputId: GmoPgEntryTransactionInputId,
  amount: Int,
  orderId: String
) extends GmoPgInput
