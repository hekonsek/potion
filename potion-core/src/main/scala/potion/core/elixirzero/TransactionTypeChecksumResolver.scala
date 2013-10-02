package potion.core.elixirzero

import potion.core.elixirzero.TransactionType.TransactionType


trait TransactionTypeChecksumResolver {

  def transactionTypeChecksum(transactionType: TransactionType): String

}
