package potion.elixirzero

import System.nanoTime
import Elixirs._

class DefaultPaymentOrderRecordGenerator(transactionTypeChecksumResolver: TransactionTypeChecksumResolver)
  extends PaymentOrderRecordGenerator {

  private def quotes(value: Any): String =
    "\"%s\"".format(value)

  private def multiLine(lines: Seq[String]): String =
    lines.mkString(multiLineSeparator)

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
    quotes(multiLine(paymentOrder.descriptionOfPayment)),
    quotes(""), quotes(""),
    quotes(transactionTypeChecksumResolver.transactionTypeChecksum(paymentOrder.transactionType)),
    quotes(paymentOrder.clientCorrelationId.getOrElse(nanoTime))
  ) mkString recordFieldsSeparator

}