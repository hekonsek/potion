package potion.core

import potion.core.elixirzero.ElixirZeros._
import potion.core.elixirzero.GenericPaymentOrder

object PaymentOrders {

  def quotes(value: Any): String =
    "\"%s\"".format(value)

  def multiLine(lines: Seq[String]): String =
    lines.mkString(multiLineSeparator)

  def paymentDescription(paymentOrder: GenericPaymentOrder): String = {
    val descriptionLines = paymentOrder.paymentSystem match {
      case PaymentSystem.sorbnet => paymentOrder.descriptionOfPayment :+ sorbnetIdentifier
      case _ => paymentOrder.descriptionOfPayment
    }
    quotes(multiLine(descriptionLines))
  }

}
