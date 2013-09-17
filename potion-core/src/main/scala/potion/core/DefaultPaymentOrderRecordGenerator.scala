package potion.core

import Elixirs._
import System.nanoTime

class DefaultPaymentOrderRecordGenerator extends PaymentOrderRecordGenerator {

  private def quotes(value: Any): String =
    "\"%s\"".format(value)

  private def multiLine(lines: Seq[String]): String =
    lines.mkString(multiLineSeparator)

  def generate(paymentOrder: PaymentOrder): String = Seq(
    paymentOrder.transactionType,
    paymentOrder.dateOfPayment,
    paymentOrder.amount,
    quotes(paymentOrder.senderBankAccountNumber),
    quotes(paymentOrder.receiverBankAccountNumber),
    quotes(multiLine(paymentOrder.senderNameAndAddress)),
    quotes(multiLine(paymentOrder.receiverNameAndAddress)),
    paymentOrder.receiverBankSettlementNumber,
    quotes(multiLine(paymentOrder.descriptionOfPayment)),
    quotes(paymentOrder.clientCorrelationId.getOrElse(nanoTime))
  ) mkString recordFieldsSeparator

}