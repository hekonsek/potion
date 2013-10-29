/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package potion.core.statement.mt940

import java.util.Date
import potion.core.statement.mt940.Mt940s._
import potion.core.statement.mt940.BalanceSign._

case class Mt940TransactionRecord(header: Mt940Header, lines: Map[String, String]) extends Mt940Record {

  val transactionDate: Date = {
    val transactionLine = lines(transactionRecordPrefix).trim
    val transactionDateSubstring = transactionLine.substring(transactionDateStartIndex, transactionDateEndIndex)
    val dateWithoutYear = transactionDateFormat.parse(transactionDateSubstring)
    dateWithoutYear.setYear(header.statementTransactionDate.getYear)
    dateWithoutYear
  }

  val balanceSign: BalanceSign = {
    val transactionLine = lines(transactionRecordPrefix).trim
    val signString = transactionLine.substring(balanceSignStartIndex, balanceSignEndIndex)
    BalanceSign.withName(signString)
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

  val simpId: String = {
    val transactionLine = lines(transactionRecordPrefix).trim
    val prefixedSimpIdStartIndex = transactionLine.indexOf(transactionSimpIdPrefix)
    if (prefixedSimpIdStartIndex == -1)
      ""
    else
      transactionLine.substring(prefixedSimpIdStartIndex + transactionSimpIdPrefix.length, transactionLine.length)
  }

}