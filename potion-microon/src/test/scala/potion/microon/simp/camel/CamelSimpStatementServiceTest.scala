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
package potion.microon.simp.camel

import org.scalatest.FunSuite
import org.apache.camel.impl.DefaultCamelContext
import scala.io.Source._
import org.scalatest.matchers.ShouldMatchers
import potion.microon.simp.api.scala.CamelSimpStatementService

class CamelSimpStatementServiceTest extends FunSuite with ShouldMatchers {

  val camelContext = new DefaultCamelContext

  val service = new CamelSimpStatementService(camelContext)

  camelContext.start()

  // Data fixtures

  val ingReferenceExample = fromInputStream(getClass.getResourceAsStream("simp_example.txt")).getLines().mkString("\n")

  val clientCode = "10500099698400000000000000"

  val simp = service.parseSimpStatement(ingReferenceExample).get

  // Tests

  test("Should parse client code.") {
    simp.clientCode should equal(clientCode)
  }

  test("Should parse transactions.") {
    expectResult(2) {
      simp.transactions.size
    }
  }

}
