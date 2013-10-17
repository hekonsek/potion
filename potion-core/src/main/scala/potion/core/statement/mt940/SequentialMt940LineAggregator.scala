package potion.core.statement.mt940

class SequentialMt940LineAggregator extends Mt940LineAggregator {

  private var aggregatedHeaderRecords = Map.empty[String, String]

  private var transactionRecords = Map.empty[String, String]

  private var transactionDescriptionRecords = Map.empty[String, String]

  def aggregate(line: Mt940Line): Option[Mt940Record] = {
    line.code match {
      case Mt940s.initRecordPrefix => aggregatedHeaderRecords += (line.code -> line.value); None
      case "25" => aggregatedHeaderRecords += (line.code -> line.value); None
      case "28C" => Some(Mt940Header(aggregatedHeaderRecords + (line.code -> line.value)))
      case "61" => Some(Mt940TransactionRecord(transactionRecords + (line.code -> line.value)))
      case Mt940s.transactionDescriptionRecordPrefix => transactionDescriptionRecords += (line.code -> line.value); None
      case Mt940s.transactionDecriptionCodeSubrecordPrefix => transactionDescriptionRecords += (line.code -> line.value); None
      case Mt940s.contractorIbanRecordPrefix => Some(Mt940TransactionDescriptionRecord(transactionDescriptionRecords + (line.code -> line.value)))
      case _ => None
    }
  }

}