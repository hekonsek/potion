package potion.core.statement.mt940

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import scala.io.Source._

@RunWith(classOf[JUnitRunner])
class DefaultMt940ParserTest extends FunSuite {

  // Collaborator fixtures

  val mt940Parser = Mt940Parser.buildDefault

  val mt940text = fromInputStream(getClass.getResourceAsStream("mt940.txt")).getLines().mkString("\n")

  val mt940 = mt940Parser.parse(mt940text)

  // Data fixtures

  val statementIban = "PL29105010381000002201994791"

  val statementSequenceNumber = 129

  val transactionId = 97201080012L

  // Tests

  test("Should parse statement IBAN.") {
    expectResult(statementIban) {
      mt940.statementIban
    }
  }

  test("Should parse statement sequence number.") {
    expectResult(statementSequenceNumber) {
      mt940.statementSequenceNumber
    }
  }

  test("Should parse transaction id number.") {
    expectResult(transactionId) {
      mt940.transactionRecords.head.transactionId
    }
  }


}
