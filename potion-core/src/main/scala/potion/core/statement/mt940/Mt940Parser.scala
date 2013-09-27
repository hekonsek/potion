package potion.core.statement.mt940

trait Mt940Parser {

  def parse(mt940: String): Mt940

}

object Mt940Parser {

  def buildDefault: Mt940Parser =
    new DefaultMt940Parser(new DefaultMt940LineParser, new SequentialMt940LineAggregator)

}