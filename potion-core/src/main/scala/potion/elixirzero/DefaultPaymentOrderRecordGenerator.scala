package potion.elixirzero

import System.nanoTime
import Elixirs._

class DefaultPaymentOrderRecordGenerator(transactionTypeChecksumResolver: TransactionTypeChecksumResolver)
  extends PaymentOrderRecordGenerator {

  def generate(paymentOrder: PaymentOrder): String = {
    val paymentRecords = paymentOrder match {
      case order: GenericPaymentOrder => Seq(
        order.transactionType.id,
        paymentOrderDateFormat.format(order.dateOfPayment),
        order.amount,
        order.senderBankSettlementNumber,
        0,
        quotes(order.senderBankAccountNumber),
        quotes(order.receiverBankAccountNumber),
        quotes(multiLine(order.senderNameAndAddress)),
        quotes(multiLine(order.receiverNameAndAddress)),
        0,
        order.receiverBankSettlementNumber,
        paymentDescription(order),
        quotes(""), quotes(""),
        quotes(transactionTypeChecksumResolver.transactionTypeChecksum(order.transactionType)),
        quotes(order.clientCorrelationId.getOrElse(nanoTime))
      )

      case order: ElixirExpressPaymentOrder => Seq(
        order.amount,
        multiLine(order.receiverNameAndAddress),
        order.senderBankAccountNumber,
        order.receiverBankAccountNumber,
        multiLine(order.descriptionOfPayment)
      )
    }
    paymentRecords.mkString(recordFieldsSeparator)
  }

  // Generator helpers

  private def quotes(value: Any): String =
    "\"%s\"".format(value)

  private def multiLine(lines: Seq[String]): String =
    lines.mkString(multiLineSeparator)

  private def paymentDescription(paymentOrder: GenericPaymentOrder): String = {
    val descriptionLines = paymentOrder.paymentSystem match {
      case PaymentSystem.sorbnet => paymentOrder.descriptionOfPayment :+ sorbnetIdentifier
      case _ => paymentOrder.descriptionOfPayment
    }
    quotes(multiLine(descriptionLines))
  }

}