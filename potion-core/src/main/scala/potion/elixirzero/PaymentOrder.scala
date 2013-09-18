package potion.elixirzero

import potion.elixirzero.Elixirs.PaymentSystem._

trait PaymentOrder {
  def paymentSystem: PaymentSystem
}
