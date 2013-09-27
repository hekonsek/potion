package potion.camel.dataformat

import org.apache.camel.spi.DataFormat
import org.apache.camel.Exchange
import java.io.{OutputStream, InputStream}
import scala.collection.JavaConversions._
import potion.core.{PaymentOrder, PaymentOrderRecordGenerator}

class PaymentOrderDataFormat(paymentOrderRecordGenerator: PaymentOrderRecordGenerator) extends DataFormat {

  def marshal(exchange: Exchange, graph: scala.Any, stream: OutputStream) {
    val records = exchange.getContext.getTypeConverter.convertTo(classOf[java.lang.Iterable[PaymentOrder]], graph)
    records.iterator.foreach(order => stream.write((paymentOrderRecordGenerator.generate(order) + "\n").getBytes))
  }

  def unmarshal(exchange: Exchange, stream: InputStream): AnyRef =
    throw new UnsupportedOperationException("%s does not support parsing of payment orders.")

}