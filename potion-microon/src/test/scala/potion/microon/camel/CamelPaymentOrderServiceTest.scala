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
