package potion.core.statement.mt940


class SequentialMt940LineAggregator extends Mt940LineAggregator {

  private var headerRecords = Map.empty[String, String]

  private var transactionRecords = Map.empty[String, String]

  def aggregate(line: Mt940Line): Option[Mt940Record] = {
    line.code match {
      case "20" => headerRecords += (line.code -> line.value); None
      case "25" => headerRecords += (line.code -> line.value); None
      case "28C" => Some(Mt940Header(headerRecords + (line.code -> line.value)))
      case "61" => Some(Mt940TransactionRecord(transactionRecords + (line.code -> line.value)))
      case _ => None
    }
  }

}