package potion.core.statement.mt940

case class Mt940(header: Mt940Header, transactionRecords: Seq[Mt940TransactionRecord], transactionDescriptionRecords: Seq[Mt940TransactionDescriptionRecord])