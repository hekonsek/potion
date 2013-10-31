package potion.core.statement.simp

import java.io.InputStream
import scala.io.Source._
import scala.None
import Simps._
import potion.core.statement.BalanceSign

class SimpParser {

  def parse(statementStream: InputStream): SimpStatement =
    fromInputStream(statementStream).getLines().foldLeft(None: Option[SimpStatement]) {
      (statement, line) => Some(assembleStatement(statement, line))
    }.get

  private def assembleStatement(statement: Option[SimpStatement], inputLine: String): SimpStatement =
    inputLine match {
      case header if header.startsWith(simpHeaderMarker) => {
        val headerWithoutMarker = header.substring(simpHeaderMarker.length)
        val headerTokens = headerWithoutMarker.split(',')
        val clientCode = headerTokens(0)
        val generationDate = generationTimestampFormat.parse(headerTokens(1))
        SimpStatement(generationDate, clientCode, Seq.empty)
      }
      case footer if footer.startsWith(simpFooterMarker) => statement.get
      case transactionLine => {
        val transactionTokens = transactionLine.split(',')
        val transaction = Transaction(
          simpAccountNumber = transactionTokens(0),
          transactionValue = transactionTokens(1).toLong,
          balanceSign = BalanceSign.withName(transactionTokens(2)),
          transactionId = transactionTokens(6)
        )
        statement.get.copy(transactions = statement.get.transactions :+ transaction)
      }
    }

}

object SimpParser {

  def apply(): SimpParser = new SimpParser()

}
