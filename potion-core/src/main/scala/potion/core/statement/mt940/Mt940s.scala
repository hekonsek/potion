package potion.core.statement.mt940

import java.text.SimpleDateFormat

object Mt940s {

  val transactionOperationCodePrefix = 'S'

  val transactionSimpIdPrefix = "//"

  val transactionDateFormatPattern = "MMdd"

  val transactionDateFormat = new SimpleDateFormat(transactionDateFormatPattern)

  val transactionDateStartIndex = 6

  val transactionDateEndIndex = 10

  val balanceSignStartIndex = 10

  val balanceSignEndIndex = 11

  val transactionValueStartIndex = 11

  val statementIbanPrefix = "25"

  val initRecordPrefix = "20"

  val transactionRecordPrefix = "61"

  val transactionDescriptionRecordPrefix = "86"

  val transactionDecriptionCodeSubrecordPrefix = "00"

  val contractorIbanRecordPrefix = "29"

}
