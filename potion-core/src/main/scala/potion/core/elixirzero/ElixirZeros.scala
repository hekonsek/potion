package potion.core.elixirzero

import java.text.SimpleDateFormat
import potion.core.PaymentOrder
import potion.core.PaymentOrders._
import java.lang.System._

object ElixirZeros {

  val recordFieldsSeparator = ","

  val multiLineSeparator = "|"

  val paymentOrderDateFormatPattern = "yyyyMMdd"

  val paymentOrderDateFormat = new SimpleDateFormat(paymentOrderDateFormatPattern)

  val sorbnetIdentifier = "SORBNET"

  def recordGenerator(transactionTypeChecksumResolver: TransactionTypeChecksumResolver): PartialFunction[PaymentOrder, Seq[Any]] = {
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
  }

}