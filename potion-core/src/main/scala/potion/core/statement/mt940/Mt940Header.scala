package potion.core.statement.mt940

import Mt940s._

case class Mt940Header(lines: Map[String, String]) extends Mt940Record {

  val statementIban: String = lines(statementIbanPrefix).trim.substring(1)

  val statementSequenceNumber: Int = lines("28C").trim.toInt

}
