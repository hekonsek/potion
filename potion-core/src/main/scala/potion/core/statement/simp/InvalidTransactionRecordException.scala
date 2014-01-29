package potion.core.statement.simp

class InvalidTransactionRecordException(message: String, record: String)
  extends RuntimeException("%s. Original record: %s".format(message, record))