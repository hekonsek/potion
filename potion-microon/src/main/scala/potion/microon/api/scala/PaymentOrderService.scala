package potion.microon.api.scala

import java.util.concurrent.Future
import potion.core.PaymentOrder

trait PaymentOrderService {

  def generatePayment(paymentOrders: Array[PaymentOrder]): Future[Array[Byte]]

}