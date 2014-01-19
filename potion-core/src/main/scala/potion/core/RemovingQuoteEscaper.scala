package potion.core

class RemovingQuoteEscaper extends QuoteEscaper {

  def escapeQuotes(value: String): String =
    value.replaceAllLiterally("\"", "")


}
