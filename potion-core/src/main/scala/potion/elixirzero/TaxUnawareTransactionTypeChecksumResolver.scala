package potion.elixirzero

import potion.elixirzero.Elixirs.TransactionType.TransactionType

class TaxUnawareTransactionTypeChecksumResolver extends TransactionTypeChecksumResolver {

  def transactionTypeChecksum(transactionType: TransactionType): String =
    transactionType match {
      case Elixirs.TransactionType.regularAndTax => "51"
      case Elixirs.TransactionType.insurance => "51"
      case Elixirs.TransactionType.paymentOrder => "01"
      case _ => throw new IllegalArgumentException("Unknown transaction type: " + transactionType)
    }

}