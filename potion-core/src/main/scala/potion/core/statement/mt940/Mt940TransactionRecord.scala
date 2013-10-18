package potion.core.statement.mt940

import java.util.Date
import Mt940s._

case class Mt940TransactionRecord(lines: Map[String, String]) extends Mt940Record {

  val transactionDate: Date = {
    val transactionLine = lines(transactionRecordPrefix).trim
    val transactionDateSubstring = transactionLine.substring(transactionDateStartIndex, transactionDateEndIndex)
    transactionDateFormat.parse(transactionDateSubstring)
  }

  val transactionValue: BigDecimal = {
    val line = lines(transactionRecordPrefix).trim
    val valueEndIndex = line.indexOf(transactionOperationCodePrefix)
    val valueString = line.substring(transactionValueStartIndex, valueEndIndex)
    val normalizedValueString = valueString.replace(',', '.')
    BigDecimal(normalizedValueString)
  }

  val transactionId: Long = {
    val transactionLine = lines(transactionRecordPrefix).trim
    val txIdBeginIndex = transactionLine.indexOf(transactionOperationCodePrefix) + 4
    val txIdEndIndex = transactionLine.indexOf('/', txIdBeginIndex)
    val finalTxIdEndIndex = if (txIdEndIndex == -1) transactionLine.length else txIdEndIndex
    transactionLine.substring(txIdBeginIndex, finalTxIdEndIndex).toLong
  }

}