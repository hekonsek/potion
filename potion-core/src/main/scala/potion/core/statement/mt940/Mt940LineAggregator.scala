package potion.core.statement.mt940

trait Mt940LineAggregator {

  def aggregate(line: Mt940Line): Option[Mt940Record]

}
