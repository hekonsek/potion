package potion.elixirzero

import java.util.Date
import potion.elixirzero.Elixirs.TransactionType.TransactionType

case class PaymentOrder(transactionType: TransactionType,
                        dateOfPayment: Date,
                        amount: Long,
                        senderBankSettlementNumber: Long,
                        senderBankAccountNumber: String,
                        receiverBankAccountNumber: String,
                        senderNameAndAddress: Seq[String],
                        receiverNameAndAddress: Seq[String],
                        receiverBankSettlementNumber: Long,
                        descriptionOfPayment: Seq[String],
                        clientCorrelationId: Option[String],
                        sorbnet: Boolean = false)