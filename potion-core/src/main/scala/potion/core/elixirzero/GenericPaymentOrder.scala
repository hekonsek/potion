package potion.core.elixirzero

import java.util.Date
import potion.core.{PaymentSystem, PaymentOrder}
import potion.core.elixirexpress.ElixirExpressPaymentOrder
import potion.core.elixirzero.ElixirZeros.TransactionType.TransactionType
import potion.core.PaymentSystem.PaymentSystem

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