package potion.core

trait PaymentOrderRecordGenerator {

  def generate(paymentOrder: PaymentOrder): String

}