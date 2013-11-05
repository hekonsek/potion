package potion.core.statement.simp

import java.util.regex.Pattern._
import RegexLineSplitter._

class RegexLineSplitter extends LineSplitter {

  def splitLine(line: String): Seq[String] =
    regex.split(line)

}

object RegexLineSplitter {

  /**
   * Explanation of regex -
   * http://stackoverflow.com/questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes .
   */
  private val regex = compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)")

  def apply(): LineSplitter = new RegexLineSplitter()

}