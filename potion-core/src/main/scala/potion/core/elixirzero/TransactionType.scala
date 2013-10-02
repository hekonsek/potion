package potion.core.elixirzero

object TransactionType extends Enumeration {
  type TransactionType = Value

  val regularAndTax = Value(110)
  val insurance = Value(120)
  val paymentOrder = Value(210)
}