package potion.camel.dataformat

import org.apache.camel.spi.DataFormat
import org.apache.camel.Exchange
import java.io.{OutputStream, InputStream}
import potion.core.statement.mt940.Mt940Parser
import scala.io.Source

class Mt940DataFormat(mt940Parser: Mt940Parser = Mt940Parser.buildDefault) extends DataFormat {

  def marshal(exchange: Exchange, graph: scala.Any, stream: OutputStream) {
    throw new UnsupportedOperationException("Parsing of payment orders is not supported")
  }

  def unmarshal(exchange: Exchange, stream: InputStream): AnyRef = {
    val statementFile = Source.fromInputStream(stream).getLines().mkString("\n")
    mt940Parser.parse(statementFile)
  }

}