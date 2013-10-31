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

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import Mt940s._
import scala.io.Source._
import potion.core.statement.BalanceSign

@RunWith(classOf[JUnitRunner])
class DefaultMt940ParserTwoTransactionsTest extends FunSuite {

  // Collaborator fixtures

  val mt940Parser = Mt940Parser.buildDefault

  val mt940text = fromInputStream(getClass.getResourceAsStream("mt940_two_transactions_example.txt")).getLines().mkString("\n")

  val mt940 = mt940Parser.parse(mt940text)

  // Data fixtures

  val statementIban = "PL87605050861120309430202189"

  val statementSequenceNumber = 44

  val statementTransactionDate = statementTransactionDateFormat.parse("130830")

  val firstBalanceSign = BalanceSign.credit

  val secondBalanceSign = BalanceSign.debit

  val firstTransactionValue = BigDecimal(2233.22)

  val secondTransactionValue = BigDecimal(3312.33)

  //  val transactionId = 97201080012L
  //
  //  val simpId = "555000000006275"
  //
  //  val transactionCode = "076"
  //
  //  val contractorIban = "19114020040000350230599137"

  // Tests

  test("Should parse statement IBAN.") {
    expectResult(statementIban) {
      mt940.header.statementIban
    }
  }

  test("Should parse statement sequence number.") {
    expectResult(statementSequenceNumber) {
      mt940.header.statementSequenceNumber
    }
  }

  test("Should parse statement transaction date.") {
    expectResult(statementTransactionDate) {
      mt940.header.statementTransactionDate
    }
  }

  test("Should parse two transactions.") {
    expectResult(2) {
      mt940.transactionRecords.size
    }
  }

  test("Should parse two transaction descriptions.") {
    expectResult(2) {
      mt940.transactionDescriptionRecords.size
    }
  }


  test("Should parse transaction date.") {
    expectResult(statementTransactionDate) {
      mt940.transactionRecords.head.transactionDate
    }
  }

  test("Should parse the first transaction balance sign.") {
    expectResult(firstBalanceSign) {
      mt940.transactionRecords.head.balanceSign
    }
  }

  test("Should parse the second transaction balance sign.") {
    expectResult(secondBalanceSign) {
      mt940.transactionRecords.last.balanceSign
    }
  }

  test("Should parse the first transaction value.") {
    expectResult(firstTransactionValue) {
      mt940.transactionRecords.head.transactionValue
    }
  }

  test("Should the second transaction value.") {
    expectResult(secondTransactionValue) {
      mt940.transactionRecords.last.transactionValue
    }
  }

  //  test("Should parse transaction id number.") {
  //    expectResult(transactionId) {
  //      mt940.transactionRecords.head.transactionId
  //    }
  //  }
  //
  //  test("Should parse SIMP id.") {
  //    expectResult(simpId) {
  //      mt940.transactionRecords.head.simpId
  //    }
  //  }
  //
  //  test("Should parse no SIMP id.") {
  //    expectResult("") {
  //      val mt940WithNoSimp = mt940text.replaceFirst("//.+\\n", "\n")
  //      val mt940 = mt940Parser.parse(mt940WithNoSimp)
  //      mt940.transactionRecords.head.simpId
  //    }
  //  }
  //
  //  test("Should parse transaction code from transaction description.") {
  //    expectResult(transactionCode) {
  //      mt940.transactionDescriptionRecords.head.transactionCode
  //    }
  //  }
  //
  //  test("Should parse contractor IBAN from transaction description.") {
  //    expectResult(contractorIban) {
  //      mt940.transactionDescriptionRecords.head.contractorIban
  //    }
  //  }

}