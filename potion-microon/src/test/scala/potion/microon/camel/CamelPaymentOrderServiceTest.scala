package potion.microon.camel

import org.scalatest.FunSuite
import org.apache.camel.impl.DefaultCamelContext
import potion.core.PaymentSystem
import potion.core.elixirzero.ElixirZeros._
import scala.io.Source._
import potion.core.elixirzero.{TransactionType, GenericPaymentOrder}
import scala.Some

class CamelPaymentOrderServiceTest extends FunSuite {

  val camelContext = new DefaultCamelContext

  val service = new CamelPaymentOrderService(camelContext)

  camelContext.start()

  test("Should generate order.") {
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
      senderNameAndAddress = Array("NAZWA_STRONY_ZLECAJACEJ", "RESZTA_NAZWY", "ULICA_STRONY_ZLECAJACEJ", "MIASTO_STRONY_ZLECAJACEJ"),
      receiverNameAndAddress = Array("NAZWA_KONTRAHENTA", "RESZTA_NAZWY", "UL._KONTRAHENTA", "MIASTO_KONTRAHENTA"),
      receiverBankSettlementNumber = 10901870,
      descriptionOfPayment = Array("OPIS_PLATNOSCI_1", "OPIS_PLATNOSCI_2", "", ""),
      clientCorrelationId = Some("CLIENTID")
    )

    expectResult(expectedFile) {
      val bytes = service.generatePayment(Array(paymentOrder, paymentOrder))
      new String(bytes.get)
    }
  }

}
