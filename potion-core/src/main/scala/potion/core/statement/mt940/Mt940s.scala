package potion.core.statement.mt940

import java.text.SimpleDateFormat

object Mt940s {

  val transactionDateFormatPattern = "MMdd"

  val transactionDateFormat = new SimpleDateFormat(transactionDateFormatPattern)

  val transactionDateStartIndex = 6

  val transactionDateEndIndex = 10

  val initRecordPrefix = "20"

  val transactionDescriptionRecordPrefix = "86"

  val transactionDecriptionCodeSubrecordPrefix = "~00"

}
