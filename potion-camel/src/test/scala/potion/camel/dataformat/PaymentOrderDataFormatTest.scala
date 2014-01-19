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
package potion.camel.dataformat

import org.apache.camel.test.junit4.CamelTestSupport
import potion.core.{RemovingQuoteEscaper, PaymentSystem, DefaultPaymentOrderRecordGenerator}
import potion.core.PaymentOrders.newLineSeparator
import potion.core.elixirzero.{TransactionType, ElixirZeros, TaxUnawareTransactionTypeChecksumResolver, GenericPaymentOrder}
import potion.core.elixirexpress.ElixirExpresses
import potion.core.elixirzero.ElixirZeros._
import org.junit.Test
import scala.io.Source._
import scala.Some
import com.google.common.collect.Lists.newArrayList
import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder, RouteBuilderSupport}

class PaymentOrderDataFormatTest extends CamelTestSupport with RouteBuilderSupport {

  // Collaborators fixture

  val transactionTypeChecksumResolver = TaxUnawareTransactionTypeChecksumResolver()

  val paymentRecordHandlers = ElixirExpresses.recordGenerator orElse ElixirZeros.recordGenerator(new RemovingQuoteEscaper, transactionTypeChecksumResolver)

  val paymentOrderRecordGenerator = new DefaultPaymentOrderRecordGenerator(paymentRecordHandlers)

  val paymentDataFormat = new PaymentOrderDataFormat(paymentOrderRecordGenerator)

  // Route fixtures

  override def createRouteBuilder() =
    new ScalaRouteBuilder() {
      from("direct:test").marshal(paymentDataFormat).to("mock:test")
    }

  // Tests

  @Test
  def shouldGenerateElixirZeroPaymentOrderFile() {
    // Given
    val ingReferenceExample = fromInputStream(getClass.getResourceAsStream("ing_elixir_example.txt")).getLines().next()
    val expectedFile = ingReferenceExample + newLineSeparator + ingReferenceExample + newLineSeparator
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
    getMockEndpoint("mock:test").expectedBodiesReceived(expectedFile)

    // When

    val orders = newArrayList(paymentOrder, paymentOrder)
    sendBody("direct:test", orders)

    // Then
    getMockEndpoint("mock:test").assertIsSatisfied()
  }

}