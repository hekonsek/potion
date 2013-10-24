package potion.microon.mt940.api.scala

import java.util.concurrent.Future
import potion.core.statement.mt940.Mt940

trait Mt940StatementService {

  def parseMt940Statement(mt940StatementFile: String): Future[Mt940]

}