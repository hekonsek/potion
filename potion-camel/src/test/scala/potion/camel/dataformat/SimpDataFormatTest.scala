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
package potion.camel.dataformat

import org.apache.camel.test.junit4.CamelTestSupport
import org.junit.{Assert, Test}
import org.apache.camel.scala.dsl.builder.{RouteBuilder => ScalaRouteBuilder, RouteBuilderSupport}
import org.apache.camel.EndpointInject
import org.apache.camel.component.mock.MockEndpoint
import Assert._
import org.apache.camel.scala.Preamble
import Preamble._
import potion.core.statement.simp.Simps._
import potion.core.statement.simp.SimpStatement

class SimpDataFormatTest extends CamelTestSupport with RouteBuilderSupport {

  // Constants

  val startTestUri = "direct:start"

  // Collaborators fixture

  val simpDataFormat = new SimpDataFormat

  @EndpointInject(uri = "mock:test")
  var mockEndpoint: MockEndpoint = _

  // Route fixtures

  override def createRouteBuilder() =
    new ScalaRouteBuilder() {
      from(startTestUri).unmarshal(simpDataFormat).to("mock:test")
    }

  // Data fixture

  def ingReferenceExample = getClass.getResourceAsStream("simp_example.txt")

  val clientCode = "10500099698400000000000000"

  val generationTimestamp = generationTimestampFormat.parse("2013-10-24")

  // Tests

  @Test
  def shouldParseSimp() {
    // Given
    mockEndpoint.expectedMessageCount(1)
    mockEndpoint.message(0).body().isInstanceOf(classOf[SimpStatement])

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
  }

  @Test
  def shouldParseClientCode() {
    // Given
    mockEndpoint.expectedMessageCount(1)

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
    assertEquals(clientCode, mockEndpoint.getExchanges.get(0).in[SimpStatement].clientCode)
  }

  @Test
  def shouldParseGenerationTimestamp() {
    // Given
    mockEndpoint.expectedMessageCount(1)

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
    assertEquals(generationTimestamp, mockEndpoint.getExchanges.get(0).in[SimpStatement].generationTimestamp)
  }

  @Test
  def shouldParseTransactions() {
    // Given
    mockEndpoint.expectedMessageCount(1)

    // When
    sendBody(startTestUri, ingReferenceExample)

    // Then
    mockEndpoint.assertIsSatisfied()
    assertEquals(2, mockEndpoint.getExchanges.get(0).in[SimpStatement].transactions.size)
  }

}