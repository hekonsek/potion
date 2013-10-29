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

import java.util.Date
import potion.core.{PaymentSystem, PaymentOrder}
import potion.core.elixirexpress.ElixirExpressPaymentOrder
import potion.core.PaymentSystem.PaymentSystem
import potion.core.elixirzero.TransactionType.TransactionType

case class GenericPaymentOrder(paymentSystem: PaymentSystem,
                               transactionType: TransactionType,
                               dateOfPayment: Date,
                               amount: Long,
                               senderBankSettlementNumber: Long,
                               senderBankAccountNumber: String,
                               receiverBankAccountNumber: String,
                               senderNameAndAddress: Array[String],
                               receiverNameAndAddress: Array[String],
                               receiverBankSettlementNumber: Long,
                               descriptionOfPayment: Array[String],
                               clientCorrelationId: Option[String]) extends PaymentOrder {

  assert(
    paymentSystem != PaymentSystem.elixirExpress,
    "To create Elixir Express payment order use: " + classOf[ElixirExpressPaymentOrder].getName
  )

}