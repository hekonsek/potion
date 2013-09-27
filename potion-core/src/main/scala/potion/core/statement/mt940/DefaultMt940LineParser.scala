package potion.core.statement.mt940

class DefaultMt940LineParser extends Mt940LineParser {

  def parseLine(line: String): Mt940Line = {
    if (line.startsWith(":")) {
      val code = line.substring(1).replaceAll(":.*", "")
      Mt940Line(code, line.substring(2 + code.length))
    } else if (line.startsWith("~")) {
      val code = line.substring(1, 3)
      Mt940Line(code, line.substring(3))
    } else {
      Mt940Line("UNKNOWN", line)
    }
  }

}