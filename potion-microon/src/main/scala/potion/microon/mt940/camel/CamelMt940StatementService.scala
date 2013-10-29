/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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