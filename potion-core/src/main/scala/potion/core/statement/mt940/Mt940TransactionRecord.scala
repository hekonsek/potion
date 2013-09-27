package potion.core.statement.mt940

import java.util.Date
import Mt940s._

case class Mt940TransactionRecord(lines: Map[String, String]) extends Mt940Record {

  val transactionDate: Date = {
    val transactionLine = lines("61").trim
    val transactionDateSubstring = transactionLine.substring(transactionDateStartIndex, transactionDateEndIndex)
    transactionDateFormat.parse(transactionDateSubstring)
  }


  val transactionId: Long = {
    val transactionLine = lines("61").trim
    val txIdBeginIndex = transactionLine.indexOf('S') + 4
    val txIdEndIndex = transactionLine.indexOf('/', txIdBeginIndex)
    val finalTxIdEndIndex = if (txIdEndIndex == -1) transactionLine.length else txIdEndIndex
    transactionLine.substring(txIdBeginIndex, finalTxIdEndIndex).toLong
  }

}