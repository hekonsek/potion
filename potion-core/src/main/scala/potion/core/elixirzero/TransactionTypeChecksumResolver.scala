package potion.core.elixirzero

import potion.core.elixirzero.ElixirZeros.TransactionType.TransactionType


trait TransactionTypeChecksumResolver {

  def transactionTypeChecksum(transactionType: TransactionType): String

}
