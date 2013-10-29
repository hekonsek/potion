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

import Mt940s._
import java.util.Date

case class Mt940Header(lines: Map[String, String] = Map.empty) extends Mt940Record {

  lazy val statementIban: String = lines(statementIbanPrefix).trim.substring(1)

  lazy val statementSequenceNumber: Int = lines(statementSequenceNumberRecordPrefix).trim.toInt

  lazy val statementTransactionDate: Date = {
    val balanceLine = lines(statementBalanceRecordPrefix).trim
    val statementTransactionDateString = balanceLine.substring(1, 7)
    statementTransactionDateFormat.parse(statementTransactionDateString)
  }

}
