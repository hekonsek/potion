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
package potion.core.statement.mt940

import scala.io.Source
import scala.collection.mutable.ListBuffer

class DefaultMt940Parser(lineParser: Mt940LineParser, lineAggregator: Mt940LineAggregator) extends Mt940Parser {

  def parse(mt940: String): Mt940 = {
    var header: Mt940Header = null
    var transactionRecords = new ListBuffer[Mt940TransactionRecord]()
    var transactionDescriptionRecords = new ListBuffer[Mt940TransactionDescriptionRecord]()
    Source.fromString(mt940).getLines().foreach {
      sourceLine =>
        val line = lineParser.parseLine(sourceLine)
        lineAggregator.aggregate(line) match {
          case Some(record) => record match {
            case h: Mt940Header => header = h
            case record: Mt940TransactionRecord => transactionRecords += record
            case record: Mt940TransactionDescriptionRecord => transactionDescriptionRecords += record
          }
          case None =>
        }
    }
    Mt940(header, transactionRecords, transactionDescriptionRecords)
  }

}