package potion.microon.mt940.camel

import org.apache.camel.{ExchangePattern, CamelContext}
import java.util.concurrent.Future
import org.apache.camel.scala.dsl.builder.RouteBuilder
import potion.camel.dataformat.Mt940DataFormat
import potion.core.statement.mt940.{Mt940, Mt940Parser}
import potion.microon.mt940.api.scala.Mt940StatementService

class CamelMt940StatementService(camelContext: CamelContext,
                                 inEndpoint: String = "direct:microon/Mt940StatementService",
                                 mt940Parser: Mt940Parser = Mt940Parser.buildDefault)
  extends Mt940StatementService {

  private val dataFormat = new Mt940DataFormat(mt940Parser)

  camelContext.addRoutes(new RouteBuilder {
    from(inEndpoint).unmarshal(dataFormat).setExchangePattern(ExchangePattern.InOut)
  })

  def parseMt940Statement(mt940StatementFile: String): Future[Mt940] =
    camelContext.createProducerTemplate().asyncRequestBody(inEndpoint, mt940StatementFile, classOf[Mt940])

}