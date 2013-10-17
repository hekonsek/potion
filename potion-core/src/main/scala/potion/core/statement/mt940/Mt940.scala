package potion.core.statement.mt940

case class Mt940(header: Mt940Header, transactionRecords: Seq[Mt940TransactionRecord], transactionDescriptionRecords: Seq[Mt940TransactionDescriptionRecord]) {

  val statementIban: String = header.lines("25").trim.substring(1)

  val statementSequenceNumber: Int = header.lines("28C").trim.toInt

}