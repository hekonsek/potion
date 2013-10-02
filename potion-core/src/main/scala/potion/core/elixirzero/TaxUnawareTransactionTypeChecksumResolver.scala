package potion.core.elixirzero

import potion.core.elixirzero.TransactionType.TransactionType


class TaxUnawareTransactionTypeChecksumResolver extends TransactionTypeChecksumResolver {

  def transactionTypeChecksum(transactionType: TransactionType): String =
    transactionType match {
      case TransactionType.regularAndTax => "51"
      case TransactionType.insurance => "51"
      case TransactionType.paymentOrder => "01"
      case _ => throw new IllegalArgumentException("Unknown transaction type: " + transactionType)
    }

}

object TaxUnawareTransactionTypeChecksumResolver {
  def apply() = new TaxUnawareTransactionTypeChecksumResolver()
}