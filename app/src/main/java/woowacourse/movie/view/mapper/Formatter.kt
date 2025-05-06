package woowacourse.movie.view.mapper

import android.icu.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Formatter {
    private val DATE_UI_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private val PRICE_UI_FORMATTER = DecimalFormat("#,###")

    @JvmStatic
    fun localDateToUI(date: LocalDate): String = date.format(DATE_UI_FORMATTER)

    @JvmStatic
    fun priceToUI(price: Int): String = PRICE_UI_FORMATTER.format(price)
}
