package potion.core

import potion.core.elixirzero.ElixirZeros._

case class DefaultPaymentOrderRecordGenerator(
                                               recordGeneratorHandler: PartialFunction[PaymentOrder, Seq[Any]]
                                               ) extends PaymentOrderRecordGenerator {

  def generate(paymentOrder: PaymentOrder): String =
    recordGeneratorHandler(paymentOrder).mkString(recordFieldsSeparator)

}