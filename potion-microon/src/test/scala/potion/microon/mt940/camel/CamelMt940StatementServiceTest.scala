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

import org.scalatest.FunSuite
import org.apache.camel.impl.DefaultCamelContext
import scala.io.Source._
import org.scalatest.matchers.ShouldMatchers

class CamelMt940StatementServiceTest extends FunSuite with ShouldMatchers {

  val camelContext = new DefaultCamelContext

  val service = new CamelMt940StatementService(camelContext)

  camelContext.start()

  // Data fixtures

  val ingReferenceExample = fromInputStream(getClass.getResourceAsStream("mt940_ing_example1_outgoing_within_poland.txt")).getLines().mkString("\n")
  val mt940 = service.parseMt940Statement(ingReferenceExample).get

  // Tests

  test("Should parse statement header.") {
    mt940.header should not be null
  }

  test("Should parse statement transactions.") {
    expectResult(1) {
      mt940.transactionRecords.size
    }
  }

  test("Should parse statement transactions descriptions.") {
    expectResult(1) {
      mt940.transactionDescriptionRecords.size
    }
  }

}
