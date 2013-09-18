package potion.elixirzero

import System.nanoTime
import Elixirs._

class DefaultPaymentOrderRecordGenerator(transactionTypeChecksumResolver: TransactionTypeChecksumResolver)
  extends PaymentOrderRecordGenerator {

  def generate(paymentOrder: PaymentOrder): String = Seq(
    paymentOrder.transactionType,
    paymentOrderDateFormat.format(paymentOrder.dateOfPayment),
    paymentOrder.amount,
    paymentOrder.senderBankSettlementNumber,
    0,
    quotes(paymentOrder.senderBankAccountNumber),
    quotes(paymentOrder.receiverBankAccountNumber),
    quotes(multiLine(paymentOrder.senderNameAndAddress)),
    quotes(multiLine(paymentOrder.receiverNameAndAddress)),
    0,
    paymentOrder.receiverBankSettlementNumber,
    paymentDescription(paymentOrder),
    quotes(""), quotes(""),
    quotes(transactionTypeChecksumResolver.transactionTypeChecksum(paymentOrder.transactionType)),
    quotes(paymentOrder.clientCorrelationId.getOrElse(nanoTime))
  ) mkString recordFieldsSeparator

  // Generator helpers

  private def quotes(value: Any): String =
    "\"%s\"".format(value)

  private def multiLine(lines: Seq[String]): String =
    lines.mkString(multiLineSeparator)

  private def paymentDescription(paymentOrder: PaymentOrder): String = {
    val descriptionLines = paymentOrder.sorbnet match {
      case true => paymentOrder.descriptionOfPayment :+ sorbnetIdentifier
      case false => paymentOrder.descriptionOfPayment
    }
    quotes(multiLine(descriptionLines))
  }

}