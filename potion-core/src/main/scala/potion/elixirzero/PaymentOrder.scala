package potion.elixirzero

import java.util.Date

case class PaymentOrder(transactionType: Int, dateOfPayment: Date, amount: Long, senderBankSettlementNumber: Long,
                        senderBankAccountNumber: String, receiverBankAccountNumber: String,
                        senderNameAndAddress: Seq[String], receiverNameAndAddress: Seq[String],
                        receiverBankSettlementNumber: Long, descriptionOfPayment: Seq[String],
                        clientCorrelationId: Option[String])