package potion.core.statement.mt940

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import Mt940s.transactionDateFormat
import scala.io.Source._

@RunWith(classOf[JUnitRunner])
class DefaultMt940ParserTest extends FunSuite {

  // Collaborator fixtures

  val mt940Parser = Mt940Parser.buildDefault

  val mt940text = fromInputStream(getClass.getResourceAsStream("mt940_ing_example1_outgoing_within_poland.txt")).getLines().mkString("\n")

  val mt940 = mt940Parser.parse(mt940text)

  // Data fixtures

  val statementIban = "PL29105010381000002201994791"

  val statementSequenceNumber = 129

  val transactionDate = transactionDateFormat.parse("0122")

  val balanceSign = BalanceSign.debit

  val transactionValue = BigDecimal(1.2)

  val transactionId = 97201080012L

  val simpId = "555000000006275"

  val transactionCode = "076"

  val contractorIban = "19114020040000350230599137"

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

  test("Should parse transaction date.") {
    expectResult(transactionDate) {
      mt940.transactionRecords.head.transactionDate
    }
  }

  test("Should parse transaction balance sign.") {
    expectResult(balanceSign) {
      mt940.transactionRecords.head.balanceSign
    }
  }

  test("Should parse transaction value.") {
    expectResult(transactionValue) {
      mt940.transactionRecords.head.transactionValue
    }
  }

  test("Should parse transaction id number.") {
    expectResult(transactionId) {
      mt940.transactionRecords.head.transactionId
    }
  }

  test("Should parse SIMP id.") {
    expectResult(simpId) {
      mt940.transactionRecords.head.simpId
    }
  }

  test("Should parse no SIMP id.") {
    expectResult("") {
      val mt940WithNoSimp = mt940text.replaceFirst("//.+\\n", "\n")
      val mt940 = mt940Parser.parse(mt940WithNoSimp)
      mt940.transactionRecords.head.simpId
    }
  }

  test("Should parse transaction code from transaction description.") {
    expectResult(transactionCode) {
      mt940.transactionDescriptionRecords.head.transactionCode
    }
  }

  test("Should parse contractor IBAN from transaction description.") {
    expectResult(contractorIban) {
      mt940.transactionDescriptionRecords.head.contractorIban
    }
  }

}