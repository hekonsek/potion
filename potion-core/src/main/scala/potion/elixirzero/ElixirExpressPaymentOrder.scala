package potion.elixirzero

import potion.elixirzero.Elixirs.PaymentSystem


case class ElixirExpressPaymentOrder(amount: Long,
                                     senderBankAccountNumber: String,
                                     receiverBankAccountNumber: String,
                                     receiverNameAndAddress: Seq[String],
                                     descriptionOfPayment: Seq[String]) extends PaymentOrder {

  def paymentSystem: PaymentSystem.PaymentSystem = PaymentSystem.elixirExpress

}