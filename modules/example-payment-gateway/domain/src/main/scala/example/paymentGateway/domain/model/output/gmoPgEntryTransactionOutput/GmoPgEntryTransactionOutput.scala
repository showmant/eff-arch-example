package example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput

sealed trait GmoPgOutput
case class GmoPgEntryTransactionOutput(
  outputId: GmoPgEntryTransactionOutputId,
  accessId: String,
  accessPass: String
) extends GmoPgOutput
