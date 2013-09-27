package potion.core

import potion.core.elixirzero.{ElixirZeros, TaxUnawareTransactionTypeChecksumResolver}
import potion.core.elixirexpress.ElixirExpresses

trait PaymentOrderRecordGenerator {

  def recordGeneratorHandler: PartialFunction[PaymentOrder, Seq[Any]]

  def generate(paymentOrder: PaymentOrder): String

}

object PaymentOrderRecordGenerator {

  def buildDefault: PaymentOrderRecordGenerator = {
    val transactionTypeChecksumResolver = TaxUnawareTransactionTypeChecksumResolver()
    val paymentRecordHandlers = ElixirExpresses.recordGenerator orElse ElixirZeros.recordGenerator(transactionTypeChecksumResolver)
    new DefaultPaymentOrderRecordGenerator(paymentRecordHandlers)
  }

}