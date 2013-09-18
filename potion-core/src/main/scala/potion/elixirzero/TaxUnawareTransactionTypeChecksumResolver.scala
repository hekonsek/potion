package potion.elixirzero

class TaxUnawareTransactionTypeChecksumResolver extends TransactionTypeChecksumResolver {

  def transactionTypeChecksum(transactionType: Int): String =
    transactionType match {
      case Elixirs.TransactionType.regularAndTax => "51"
      case Elixirs.TransactionType.insurance => "51"
      case Elixirs.TransactionType.paymentOrder => "01"
      case _ => throw new IllegalArgumentException("Unknown transaction type: " + transactionType)
    }

}