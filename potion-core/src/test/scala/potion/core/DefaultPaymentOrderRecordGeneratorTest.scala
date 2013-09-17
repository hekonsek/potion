package potion.core

import org.scalatest.FunSuite
import scala.io.Source.fromInputStream
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Elixirs._

@RunWith(classOf[JUnitRunner])
class DefaultPaymentOrderRecordGeneratorTest extends FunSuite {

  val transactionTypeChecksumResolver = new TaxUnawareTransactionTypeChecksumResolver

  val paymentOrderRecordGenerator = new DefaultPaymentOrderRecordGenerator(transactionTypeChecksumResolver)

  test("Should generate example from ING specification.") {
    val ingReferenceExample = fromInputStream(getClass.getResourceAsStream("ing_elixir_example.txt")).getLines().next()
    val paymentOrder = PaymentOrder(
      transactionType = 110,
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

    expectResult(ingReferenceExample) {
      paymentOrderRecordGenerator.generate(paymentOrder)
    }
  }

}
