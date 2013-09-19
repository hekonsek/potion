package potion.core.elixirexpress

import potion.core.{PaymentOrders, PaymentOrder}
import PaymentOrders._

object ElixirExpresses {

  val recordGenerator: PartialFunction[PaymentOrder, Seq[Any]] = {
    case order: ElixirExpressPaymentOrder => Seq(
      order.amount,
      multiLine(order.receiverNameAndAddress),
      order.senderBankAccountNumber,
      order.receiverBankAccountNumber,
      multiLine(order.descriptionOfPayment)
    )
  }

}