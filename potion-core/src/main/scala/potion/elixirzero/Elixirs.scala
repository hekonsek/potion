package potion.elixirzero

import java.text.SimpleDateFormat

object Elixirs {

  val recordFieldsSeparator = ","

  val multiLineSeparator = "|"

  val paymentOrderDateFormatPattern = "yyyyMMdd"

  val paymentOrderDateFormat = new SimpleDateFormat(paymentOrderDateFormatPattern)

  val sorbnetIdentifier = "SORBNET"

  object TransactionType extends Enumeration {
    type TransactionType = Value

    val regularAndTax = Value(110)
    val insurance = Value(120)
    val paymentOrder = Value(210)
  }

  object PaymentSystem extends Enumeration {
    type PaymentSystem = Value

    val elixir0, elixirExpress, sorbnet = Value
  }

}
