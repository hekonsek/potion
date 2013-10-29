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

class SequentialMt940LineAggregator extends Mt940LineAggregator {

  private var header = Mt940Header()

  private var transactionRecords = Map.empty[String, String]

  private var transactionDescriptionRecords = Map.empty[String, String]

  def aggregate(line: Mt940Line): Option[Mt940Record] = {
    line.code match {
      case Mt940s.initRecordPrefix => header = header.copy(lines = header.lines + (line.code -> line.value)); None
      case Mt940s.statementIbanPrefix => header = header.copy(lines = header.lines + (line.code -> line.value)); None
      case Mt940s.statementSequenceNumberRecordPrefix => header = header.copy(lines = header.lines + (line.code -> line.value)); None
      case Mt940s.statementBalanceRecordPrefix => header = header.copy(lines = header.lines + (line.code -> line.value)); Some(header)
      case "61" => Some(Mt940TransactionRecord(header, transactionRecords + (line.code -> line.value)))
      case Mt940s.transactionDescriptionRecordPrefix => transactionDescriptionRecords += (line.code -> line.value); None
      case Mt940s.transactionDecriptionCodeSubrecordPrefix => transactionDescriptionRecords += (line.code -> line.value); None
      case Mt940s.contractorIbanRecordPrefix => Some(Mt940TransactionDescriptionRecord(transactionDescriptionRecords + (line.code -> line.value)))
      case _ => None
    }
  }

}