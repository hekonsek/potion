package potion.core.statement.mt940

import java.text.SimpleDateFormat

object Mt940s {

  val transactionOperationCodePrefix = 'S'

  val transactionDateFormatPattern = "MMdd"

  val transactionDateFormat = new SimpleDateFormat(transactionDateFormatPattern)

  val transactionDateStartIndex = 6

  val transactionDateEndIndex = 10

  val transactionValueStartIndex = 11

  val initRecordPrefix = "20"

  val transactionRecordPrefix = "61"

  val transactionDescriptionRecordPrefix = "86"

  val transactionDecriptionCodeSubrecordPrefix = "00"

  val contractorIbanRecordPrefix = "29"

}
