package potion.core.statement.simp

import potion.core.statement.BalanceSign.BalanceSign

case class Transaction(simpAccountNumber: String, transactionValue: Long, balanceSign: BalanceSign, transactionId: String)