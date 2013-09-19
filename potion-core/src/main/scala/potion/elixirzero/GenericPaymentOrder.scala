package potion.elixirzero

import java.util.Date
import potion.elixirzero.Elixirs.PaymentSystem
import potion.elixirzero.Elixirs.TransactionType.TransactionType
import potion.elixirzero.Elixirs.PaymentSystem.PaymentSystem

case class GenericPaymentOrder(paymentSystem: PaymentSystem,
                               transactionType: TransactionType,
                               dateOfPayment: Date,
                               amount: Long,
                               senderBankSettlementNumber: Long,
                               senderBankAccountNumber: String,
                               receiverBankAccountNumber: String,
                               senderNameAndAddress: Seq[String],
                               receiverNameAndAddress: Seq[String],
                               receiverBankSettlementNumber: Long,
                               descriptionOfPayment: Seq[String],
                               clientCorrelationId: Option[String]) extends PaymentOrder {

  assert(
    paymentSystem != PaymentSystem.elixirExpress,
    "To create Elixir Express payment order use: " + classOf[ElixirExpressPaymentOrder].getName
  )

}