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

import potion.microon.api.scala.PaymentOrderService
import org.apache.camel.{ExchangePattern, CamelContext}
import potion.core.{PaymentOrderRecordGenerator, PaymentOrder}
import java.util.concurrent.Future
import org.apache.camel.scala.dsl.builder.RouteBuilder
import potion.camel.dataformat.PaymentOrderDataFormat

class CamelPaymentOrderService(camelContext: CamelContext,
                               inEndpoint: String = "direct:microon/PaymentOrderService",
                               paymentOrderRecordGenerator: PaymentOrderRecordGenerator = PaymentOrderRecordGenerator.buildDefault)
  extends PaymentOrderService {

  private val dataFormat = new PaymentOrderDataFormat(paymentOrderRecordGenerator)

  camelContext.addRoutes(new RouteBuilder {
    from(inEndpoint).marshal(dataFormat).setExchangePattern(ExchangePattern.InOut)
  })

  def generatePayment(paymentOrders: Array[PaymentOrder]): Future[Array[Byte]] =
    camelContext.createProducerTemplate().asyncRequestBody(inEndpoint, paymentOrders, classOf[Array[Byte]])

}