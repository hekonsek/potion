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
package potion.core.statement.simp

import java.io.InputStream
import scala.io.Source._
import scala.None
import Simps._
import potion.core.statement.BalanceSign

class SimpParser(lineSplitter: LineSplitter = RegexLineSplitter()) {

  def parse(statementStream: InputStream): SimpStatement =
    fromInputStream(statementStream).getLines().map(_.trim).filter(!_.isEmpty).foldLeft(None: Option[SimpStatement]) {
      (statement, line) => Some(assembleStatement(statement, line))
    }.get

  private def assembleStatement(statement: Option[SimpStatement], inputLine: String): SimpStatement =
    inputLine match {
      case header if header.startsWith(simpHeaderMarker) => {
        val headerWithoutMarker = header.substring(simpHeaderMarker.length)
        val headerTokens = lineSplitter.splitLine(headerWithoutMarker)
        val clientCode = headerTokens(0)
        val generationDate = generationTimestampFormat.parse(headerTokens(1))
        SimpStatement(generationDate, clientCode, Seq.empty)
      }
      case footer if footer.startsWith(simpFooterMarker) => statement.get
      case transactionLine => {
        val transactionTokens = lineSplitter.splitLine(transactionLine)
        val transaction = Transaction(
          simpAccountNumber = transactionTokens(0),
          transactionValue = transactionTokens(1).toLong,
          balanceSign = BalanceSign.withName(transactionTokens(2)),
          currencyDate = transactionCurrencyDateFormat.parse(transactionTokens(4)),
          transactionId = transactionTokens(6),
          contractorAccountNumber = transactionTokens(8).normalizedToken,
          transactionDescription = transactionTokens.slice(13, 16).map(_.normalizedToken)
        )
        statement.get.copy(transactions = statement.get.transactions :+ transaction)
      }
    }

}

object SimpParser {

  def apply(): SimpParser = new SimpParser()

}
