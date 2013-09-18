package potion.elixirzero

trait PaymentOrderRecordGenerator {

  def generate(paymentOrder: PaymentOrder): String

}