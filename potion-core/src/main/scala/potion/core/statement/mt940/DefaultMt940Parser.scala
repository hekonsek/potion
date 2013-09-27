package potion.core.statement.mt940

import scala.io.Source
import scala.collection.mutable.ListBuffer

class DefaultMt940Parser(lineParser: Mt940LineParser, lineAggregator: Mt940LineAggregator) extends Mt940Parser {

  def parse(mt940: String): Mt940 = {
    var header: Mt940Header = null
    var transactionRecords = new ListBuffer[Mt940TransactionRecord]()
    Source.fromString(mt940).getLines().foreach {
      l =>
        val line = lineParser.parseLine(l)
        lineAggregator.aggregate(line) match {
          case Some(x) => x match {
            case h: Mt940Header => header = h
            case record: Mt940TransactionRecord => transactionRecords += record
          }
          case None =>
        }
    }
    Mt940(header, transactionRecords)
  }

}