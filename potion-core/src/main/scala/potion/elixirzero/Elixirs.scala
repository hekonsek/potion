package potion.elixirzero

import java.text.SimpleDateFormat

object Elixirs {

  object TransactionType {
    val regularAndTax = 110
    val insurance = 120
    val paymentOrder = 210
  }

  val recordFieldsSeparator = ","

  val multiLineSeparator = "|"

  val paymentOrderDateFormatPattern = "yyyyMMdd"

  val paymentOrderDateFormat = new SimpleDateFormat(paymentOrderDateFormatPattern)

  val sorbnetIdentifier = "SORBNET"

}
