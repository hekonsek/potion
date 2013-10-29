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

import java.text.SimpleDateFormat

object Mt940s {

  val transactionOperationCodePrefix = 'S'

  val transactionSimpIdPrefix = "//"

  val transactionDateFormatPattern = "MMdd"

  val transactionDateFormat = new SimpleDateFormat(transactionDateFormatPattern)

  val statementTransactionDateFormatPattern = "yyMMdd"

  val statementTransactionDateFormat = new SimpleDateFormat(statementTransactionDateFormatPattern)

  val transactionDateStartIndex = 6

  val transactionDateEndIndex = 10

  val balanceSignStartIndex = 10

  val balanceSignEndIndex = 11

  val transactionValueStartIndex = 11

  val initRecordPrefix = "20"

  val statementIbanPrefix = "25"

  val statementSequenceNumberRecordPrefix = "28C"

  val statementBalanceRecordPrefix = "60F"

  val transactionRecordPrefix = "61"

  val transactionDescriptionRecordPrefix = "86"

  val transactionDecriptionCodeSubrecordPrefix = "00"

  val contractorIbanRecordPrefix = "29"

}
