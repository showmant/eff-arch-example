package example.paymentGateway.domain.model.output.gmoPgEntryTransactionOutput

sealed trait GmoPgOutput
case class GmoPgEntryTransactionOutput(
                                        accessId: String,
                                        accessPass: String
                                      ) extends GmoPgOutput
