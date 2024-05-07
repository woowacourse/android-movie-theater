package woowacourse.movie.ui

import java.lang.IllegalStateException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class Currency private constructor() {
    companion object {
        private val koreaCurrencyFormat = DecimalFormat("#,###원")

        private var muFormat: MutableMap<String, NumberFormat> =
            mutableMapOf(
                Locale.KOREA.country to koreaCurrencyFormat,
            )

        fun of(country: String): NumberFormat {
            if (muFormat[country] == null) {
                muFormat[country] = NumberFormat.getCurrencyInstance(Locale.getDefault())
            }
            return muFormat[country] ?: throw IllegalStateException("Not found country")
        }
    }
}
