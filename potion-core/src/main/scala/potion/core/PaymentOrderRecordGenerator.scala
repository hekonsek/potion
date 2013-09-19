package potion.core

trait PaymentOrderRecordGenerator {

  def recordGeneratorHandler: PartialFunction[PaymentOrder, Seq[Any]]

  def generate(paymentOrder: PaymentOrder): String

}