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

import org.apache.camel.spi.DataFormat
import org.apache.camel.Exchange
import java.io.{OutputStream, InputStream}
import scala.collection.JavaConversions._
import potion.core.{PaymentOrder, PaymentOrderRecordGenerator}
import potion.core.PaymentOrders.newLineSeparator
import java.nio.charset.Charset

class PaymentOrderDataFormat(paymentOrderRecordGenerator: PaymentOrderRecordGenerator, charset: Charset = Charset.forName("windows-1250")) extends DataFormat {

  def marshal(exchange: Exchange, graph: scala.Any, stream: OutputStream) {
    val records = exchange.getContext.getTypeConverter.convertTo(classOf[java.util.List[PaymentOrder]], graph)
    records.iterator.foreach(order => stream.write((paymentOrderRecordGenerator.generate(order) + newLineSeparator).getBytes(charset)))
  }

  def unmarshal(exchange: Exchange, stream: InputStream): AnyRef =
    throw new UnsupportedOperationException("%s does not support parsing of payment orders.")

}