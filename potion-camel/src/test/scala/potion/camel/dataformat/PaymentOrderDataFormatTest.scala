package potion.camel.dataformat

import org.apache.camel.test.junit4.CamelTestSupport
import org.apache.camel.builder.RouteBuilder
import potion.core.PaymentSystem
import potion.core.elixirzero.{ElixirZeros, TaxUnawareTransactionTypeChecksumResolver}
import potion.core.elixirexpress.ElixirExpresses
import potion.core.elixirzero.ElixirZeros._
import org.junit.Test
import scala.io.Source._
import potion.core.DefaultPaymentOrderRecordGenerator
import potion.core.elixirzero.GenericPaymentOrder
import scala.Some
import com.google.common.collect.Lists.newArrayList

class PaymentOrderDataFormatTest extends CamelTestSupport {

  // Collaborators fixture

  val transactionTypeChecksumResolver = TaxUnawareTransactionTypeChecksumResolver()

  val paymentRecordHandlers = ElixirExpresses.recordGenerator orElse ElixirZeros.recordGenerator(transactionTypeChecksumResolver)

  val paymentOrderRecordGenerator = new DefaultPaymentOrderRecordGenerator(paymentRecordHandlers)

  // Route fixtures

  override def createRouteBuilder(): RouteBuilder =
    new RouteBuilder() {
      def configure() {
        from("direct:test").marshal(new PaymentOrderDataFormat(paymentOrderRecordGenerator)).to("mock:test")
      }
    }

  // Tests

  @Test
  def shouldGenerateElixirZeroPaymentOrderFile() {
    // Given
    val ingReferenceExample = fromInputStream(getClass.getResourceAsStream("ing_elixir_example.txt")).getLines().next()
    val expectedFile = "%s\n%s\n".format(ingReferenceExample, ingReferenceExample)
    val paymentOrder = GenericPaymentOrder(
      paymentSystem = PaymentSystem.elixir0,
      transactionType = TransactionType.regularAndTax,
      dateOfPayment = paymentOrderDateFormat.parse("20040510"),
      amount = 403595,
      senderBankSettlementNumber = 10501038,
      senderBankAccountNumber = "29105010381000002201994791",
      receiverBankAccountNumber = "40109018700000000100198454",
      senderNameAndAddress = Seq("NAZWA_STRONY_ZLECAJACEJ", "RESZTA_NAZWY", "ULICA_STRONY_ZLECAJACEJ", "MIASTO_STRONY_ZLECAJACEJ"),
      receiverNameAndAddress = Seq("NAZWA_KONTRAHENTA", "RESZTA_NAZWY", "UL._KONTRAHENTA", "MIASTO_KONTRAHENTA"),
      receiverBankSettlementNumber = 10901870,
      descriptionOfPayment = Seq("OPIS_PLATNOSCI_1", "OPIS_PLATNOSCI_2", "", ""),
      clientCorrelationId = Some("CLIENTID")
    )
    getMockEndpoint("mock:test").expectedBodiesReceived(expectedFile)

    // When

    val orders = newArrayList(paymentOrder, paymentOrder)
    sendBody("direct:test", orders)

    // Then
    getMockEndpoint("mock:test").assertIsSatisfied()
  }

}