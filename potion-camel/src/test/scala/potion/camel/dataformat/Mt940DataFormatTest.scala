package potion.camel.dataformat

import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.{Assert, Test}
import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder, RouteBuilderSupport}
import potion.core.statement.mt940.Mt940
import org.apache.camel.EndpointInject
import org.apache.camel.component.mock.MockEndpoint
import Assert._
import org.apache.camel.scala.Preamble
import Preamble._

class Mt940DataFormatTest extends CamelTestSupport with RouteBuilderSupport {

  // Constants

  val startTestUri = "direct:start"

  // Collaborators fixture

  val mt940DataFormat = new Mt940DataFormat

  @EndpointInject(uri = "mock:test")
  var mockEndpoint: MockEndpoint = _

  // Route fixtures

  override def createRouteBuilder() =
    new ScalaRouteBuilder() {
      from(startTestUri).unmarshal(mt940DataFormat).to("mock:test")
    }

  // Data fixture

  def ingReferenceExample = getClass.getResourceAsStream("mt940_ing_example1_outgoing_within_poland.txt")

  // Tests

  @Test
  def shouldParseMt940() {
    // Given
    mockEndpoint.expectedMessageCount(1)
    mockEndpoint.message(0).body().isInstanceOf(classOf[Mt940])

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
  }

  @Test
  def shouldParseMt940Header() {
    // Given
    mockEndpoint.expectedMessageCount(1)

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
    assertNotNull(mockEndpoint.getExchanges.get(0).in[Mt940].header)
  }

  @Test
  def shouldParseMt940transaction() {
    // Given
    mockEndpoint.expectedMessageCount(1)

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
    assertEquals(1, mockEndpoint.getExchanges.get(0).in[Mt940].transactionRecords.size)
  }

  @Test
  def shouldParseMt940transactionDescription() {
    // Given
    mockEndpoint.expectedMessageCount(1)

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
    assertEquals(1, mockEndpoint.getExchanges.get(0).in[Mt940].transactionDescriptionRecords.size)
  }

}