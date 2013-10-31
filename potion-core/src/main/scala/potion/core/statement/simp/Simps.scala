package potion.core.statement.simp

import java.text.{SimpleDateFormat, DateFormat}

object Simps {

  val generationTimestampFormatPattern = "yyyy-MM-dd"

  val generationTimestampFormat: DateFormat = new SimpleDateFormat(generationTimestampFormatPattern)

  val simpHeaderMarker = "<SIMP2>"

  val simpFooterMarker = "</SIMP2>"

}
