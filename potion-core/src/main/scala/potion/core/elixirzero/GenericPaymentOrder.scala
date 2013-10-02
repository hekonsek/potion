package potion.core.elixirzero

import java.util.Date
import potion.core.{PaymentSystem, PaymentOrder}
import potion.core.elixirexpress.ElixirExpressPaymentOrder
import potion.core.PaymentSystem.PaymentSystem
import potion.core.elixirzero.TransactionType.TransactionType

case class GenericPaymentOrder(paymentSystem: PaymentSystem,
                               transactionType: TransactionType,
                               dateOfPayment: Date,
                               amount: Long,
                               senderBankSettlementNumber: Long,
                               senderBankAccountNumber: String,
                               receiverBankAccountNumber: String,
                               senderNameAndAddress: Array[String],
                               receiverNameAndAddress: Array[String],
                               receiverBankSettlementNumber: Long,
                               descriptionOfPayment: Array[String],
                               clientCorrelationId: Option[String]) extends PaymentOrder {

  assert(
    paymentSystem != PaymentSystem.elixirExpress,
    "To create Elixir Express payment order use: " + classOf[ElixirExpressPaymentOrder].getName
  )

}