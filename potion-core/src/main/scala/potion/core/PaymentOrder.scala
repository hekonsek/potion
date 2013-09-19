package potion.core

import potion.core.PaymentSystem.PaymentSystem

trait PaymentOrder {
  def paymentSystem: PaymentSystem
}