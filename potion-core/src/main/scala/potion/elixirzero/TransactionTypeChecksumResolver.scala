package potion.elixirzero

import potion.elixirzero.Elixirs.TransactionType.TransactionType

trait TransactionTypeChecksumResolver {

  def transactionTypeChecksum(transactionType: TransactionType): String

}
