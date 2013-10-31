package potion.core.statement.simp

import java.util.Date

case class SimpStatement(generationTimestamp: Date, clientCode: String, transactions: Seq[Transaction])
