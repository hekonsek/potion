package potion.core

import java.util.Date

case class PaymentOrder(transactionType: Int, dateOfPayment: Date, amount: Long,
                        rawSenderBankAccountNumber: String, rawReceiverBankAccountNumber: String,
                        senderNameAndAddress: Seq[String], receiverNameAndAddress: Seq[String],
                        receiverBankSettlementNumber: Long, descriptionOfPayment: Seq[String],
                        clientCorrelationId: Option[String])