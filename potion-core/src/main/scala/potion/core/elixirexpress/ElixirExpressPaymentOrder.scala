package potion.core.elixirexpress

import potion.core.PaymentOrder
import potion.core.PaymentSystem.{PaymentSystem, elixirExpress}


case class ElixirExpressPaymentOrder(amount: Long,
                                     senderBankAccountNumber: String,
                                     receiverBankAccountNumber: String,
                                     receiverNameAndAddress: Seq[String],
                                     descriptionOfPayment: Seq[String]) extends PaymentOrder {

  def paymentSystem: PaymentSystem = elixirExpress

}