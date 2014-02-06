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

import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import Simps._
import potion.core.statement.BalanceSign._

@RunWith(classOf[JUnitRunner])
class SimpParserTest extends FunSuite {

  val simpParser = SimpParser()

  val statement = simpParser.parse(getClass.getResourceAsStream("simp_example.txt"))

  val generationDate = generationTimestampFormat.parse("2013-10-24")

  val clientCode = "10500099698400000000000000"

  val simpAccountNumber = "04105000996984001841000783"

  val transactionValue = 482775

  val transactionCurrency = "PLN"

  val transactionId = "972019605945"

  val transactionContractorAccountNumber = "49103015820000000855156008"

  val transactionCurrencyDate = transactionCurrencyDateFormat.parse("2013-10-24")

  val transactionDescription = "FV 1/2/2013 FOO BAR, BAZ QUX"

  // Tests

  test("Should parse statement generation date.") {
    expectResult(generationDate) {
      statement.generationTimestamp
    }
  }

  test("Should parse statement client code.") {
    expectResult(clientCode) {
      statement.clientCode
    }
  }

  test("Should parse transaction records.") {
    expectResult(2) {
      statement.transactions.size
    }
  }

  test("Should parse transaction SIMP account number.") {
    expectResult(simpAccountNumber) {
      statement.transactions.head.simpAccountNumber
    }
  }

  test("Should parse transaction value.") {
    expectResult(transactionValue) {
      statement.transactions.head.transactionValue
    }
  }

  test("Should parse transaction balance sign.") {
    expectResult(credit) {
      statement.transactions.head.balanceSign
    }
  }

  test("Should parse transaction currency.") {
    expectResult(transactionCurrency) {
      statement.transactions.head.currency
    }
  }

  test("Should parse transaction currency date.") {
    expectResult(transactionCurrencyDate) {
      statement.transactions.head.currencyDate
    }
  }

  test("Should parse transaction id.") {
    expectResult(transactionId) {
      statement.transactions.head.transactionId
    }
  }

  test("Should parse transaction contractor account number.") {
    expectResult(transactionContractorAccountNumber) {
      statement.transactions.head.contractorAccountNumber
    }
  }

  test("Should parse transaction description.") {
    expectResult(transactionDescription) {
      statement.transactions.head.flatTransactionDescription
    }
  }

  test("Should parse transaction description 2.") {
    intercept[InvalidTransactionRecordException] {
      simpParser.parse(getClass.getResourceAsStream("simp_invalid_record_example.txt"))
    }
  }

}