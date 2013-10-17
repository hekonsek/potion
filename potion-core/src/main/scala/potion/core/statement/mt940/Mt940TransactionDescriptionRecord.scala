package potion.core.statement.mt940

case class Mt940TransactionDescriptionRecord(lines: Map[String, String]) extends Mt940Record {

  val transactionCode: String =
    lines(Mt940s.transactionDescriptionRecordPrefix).substring(0, 3)

}
