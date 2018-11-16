package example.paymentGateway.domain.model.input.gmoPgEntryTransactionInput

sealed trait GmoPgInput

case class GmoPgEntryTransactionInput(
                                       amount: Int,
                                       orderId: String
                                     ) extends GmoPgInput

