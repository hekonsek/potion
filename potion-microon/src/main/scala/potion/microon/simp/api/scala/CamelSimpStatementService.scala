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
package potion.microon.simp.api.scala

import org.apache.camel.{ExchangePattern, CamelContext}
import java.util.concurrent.Future
import org.apache.camel.scala.dsl.builder.RouteBuilder
import potion.camel.dataformat.SimpDataFormat
import potion.core.statement.simp.{SimpStatement, SimpParser}
import potion.microon.simp.camel.api.scala.SimpStatementService

class CamelSimpStatementService(camelContext: CamelContext,
                                inEndpoint: String = "direct:microon/simpStatementService",
                                simpParser: SimpParser = SimpParser())
  extends SimpStatementService {

  private val dataFormat = new SimpDataFormat(simpParser)

  camelContext.addRoutes(new RouteBuilder {
    from(inEndpoint).unmarshal(dataFormat).setExchangePattern(ExchangePattern.InOut)
  })

  def parseSimpStatement(simpStatementFile: String): Future[SimpStatement] =
    camelContext.createProducerTemplate().asyncRequestBody(inEndpoint, simpStatementFile, classOf[SimpStatement])

}