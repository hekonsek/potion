package potion.elixirzero

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

    expectResult(ingReferenceExample) {
      paymentOrderRecordGenerator.generate(paymentOrder)
    }
  }

  test("Should generate Sorbnet example #1.") {
    val sorbnetReferenceExample = fromInputStream(getClass.getResourceAsStream("sorbnet_example_01.txt")).getLines().next()
    val paymentOrder = GenericPaymentOrder(
      paymentSystem = PaymentSystem.sorbnet,
      transactionType = TransactionType.regularAndTax,
      dateOfPayment = paymentOrderDateFormat.parse("20130701"),
      amount = 45000000,
      senderBankSettlementNumber = 10501038,
      senderBankAccountNumber = "29105010382000002202994792",
      receiverBankAccountNumber = "27105012141020009022338232",
      senderNameAndAddress = Seq("FOO BANK SA", "FOO STREET 132", "01-633 CRACOW"),
      receiverNameAndAddress = Seq("JOHN FOO", "BAR STREET ", "CRACOW 02-321"),
      receiverBankSettlementNumber = 33301222,
      descriptionOfPayment = Seq("Some", "money for you", "to buy new shoes"),
      clientCorrelationId = Some("clientId")
    )

    expectResult(sorbnetReferenceExample) {
      paymentOrderRecordGenerator.generate(paymentOrder)
    }
  }

  test("Should generate Sorbnet example #2.") {
    val sorbnetReferenceExample = fromInputStream(getClass.getResourceAsStream("sorbnet_example_02.txt")).getLines().next()
    val paymentOrder = GenericPaymentOrder(
      paymentSystem = PaymentSystem.sorbnet,
      transactionType = TransactionType.regularAndTax,
      dateOfPayment = paymentOrderDateFormat.parse("20130701"),
      amount = 47498966,
      senderBankSettlementNumber = 10554038,
      senderBankAccountNumber = "29105015781000011201994541",
      receiverBankAccountNumber = "07157012211004609098938672",
      senderNameAndAddress = Seq("BANK BANK INC", "BANK STREET 666", "66-321 CRACOW"),
      receiverNameAndAddress = Seq("  CUSTOMER", "STREET", "CITY 12-345"),
      receiverBankSettlementNumber = 11101214,
      descriptionOfPayment = Seq("Some go", "od reason", "to pay money"),
      clientCorrelationId = Some("clientId")
    )

    expectResult(sorbnetReferenceExample) {
      paymentOrderRecordGenerator.generate(paymentOrder)
    }
  }

  test("Should generate Elixir Express example #1.") {
    val elixirExpressExample = fromInputStream(getClass.getResourceAsStream("elixirexpress_example_01.txt")).getLines().next()
    val paymentOrder = ElixirExpressPaymentOrder(
      amount = 87024,
      receiverNameAndAddress = Seq("NAME ", "STREET 12", "WARSAW 12-123"),
      senderBankAccountNumber = "29105946581000002201994791",
      receiverBankAccountNumber = "07102212754001209092338632",
      descriptionOfPayment = Seq("Description", "of the payment,", "and so forth")
    )

    expectResult(elixirExpressExample) {
      paymentOrderRecordGenerator.generate(paymentOrder)
    }
  }

}
