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
package potion.core.elixirzero

import java.text.SimpleDateFormat
import potion.core.PaymentOrder
import potion.core.PaymentOrders._
import java.lang.System._

object ElixirZeros {

  val recordFieldsSeparator = ","

  val multiLineSeparator = "|"

  val paymentOrderDateFormatPattern = "yyyyMMdd"

  val paymentOrderDateFormat = new SimpleDateFormat(paymentOrderDateFormatPattern)

  val sorbnetIdentifier = "SORBNET"

  def recordGenerator(transactionTypeChecksumResolver: TransactionTypeChecksumResolver): PartialFunction[PaymentOrder, Seq[Any]] = {
    case order: GenericPaymentOrder => Seq(
      order.transactionType.id,
      paymentOrderDateFormat.format(order.dateOfPayment),
      order.amount,
      order.senderBankSettlementNumber,
      0,
      quotes(order.senderBankAccountNumber),
      quotes(order.receiverBankAccountNumber),
      quotes(multiLine(order.senderNameAndAddress)),
      quotes(multiLine(order.receiverNameAndAddress)),
      0,
      order.receiverBankSettlementNumber,
      paymentDescription(order),
      quotes(""), quotes(""),
      quotes(transactionTypeChecksumResolver.transactionTypeChecksum(order.transactionType)),
      quotes(order.clientCorrelationId.getOrElse(nanoTime))
    )
  }

}