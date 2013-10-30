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

import scala.Some
import Mt940s._

class SequentialMt940LineAggregator extends Mt940LineAggregator {

  private var header = Mt940Header()

  private var transactionRecords = Map.empty[String, String]

  private var transactionDescriptionRecords = Map.empty[String, String]

  def aggregate(line: Mt940Line): Option[Mt940Record] = {
    line.code match {
      case `initRecordPrefix` => header = header.copy(lines = header.lines + (line.code -> line.value)); None
      case `statementIbanPrefix` => header = header.copy(lines = header.lines + (line.code -> line.value)); None
      case `statementSequenceNumberRecordPrefix` => header = header.copy(lines = header.lines + (line.code -> line.value)); None
      case `statementBalanceRecordPrefix` => header = header.copy(lines = header.lines + (line.code -> line.value)); Some(header)
      case `transactionRecordPrefix` => Some(Mt940TransactionRecord(header, transactionRecords + (line.code -> line.value)))
      case `transactionDescriptionRecordPrefix` => transactionDescriptionRecords += (line.code -> line.value); None
      case `transactionDecriptionCodeSubrecordPrefix` => transactionDescriptionRecords += (line.code -> line.value); None
      case `contractorIbanRecordPrefix` => transactionDescriptionRecords += (line.code -> line.value); None
      case `contractorDescriptionContinuationRecordPrefix` => Some(Mt940TransactionDescriptionRecord(transactionDescriptionRecords))
      case _ => None
    }
  }

}