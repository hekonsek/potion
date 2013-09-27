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