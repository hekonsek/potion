package potion.microon.mt940.camel

import org.scalatest.FunSuite
import org.apache.camel.impl.DefaultCamelContext
import scala.io.Source._
import org.scalatest.matchers.ShouldMatchers

class CamelMt940StatementServiceTest extends FunSuite with ShouldMatchers {

  val camelContext = new DefaultCamelContext

  val service = new CamelMt940StatementService(camelContext)

  camelContext.start()

  // Data fixtures

  val ingReferenceExample = fromInputStream(getClass.getResourceAsStream("mt940_ing_example1_outgoing_within_poland.txt")).getLines().mkString("\n")
  val mt940 = service.parseMt940Statement(ingReferenceExample).get

  // Tests

  test("Should parse statement header.") {
    mt940.header should not be null
  }

  test("Should parse statement transactions.") {
    expectResult(1) {
      mt940.transactionRecords.size
    }
  }

  test("Should parse statement transactions descriptions.") {
    expectResult(1) {
      mt940.transactionDescriptionRecords.size
    }
  }

}
