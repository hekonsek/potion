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

  val transactionId = "972019605945"

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

  test("Should parse transaction id.") {
    expectResult(transactionId) {
      statement.transactions.head.transactionId
    }
  }

}