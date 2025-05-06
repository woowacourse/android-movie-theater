package woowacourse.movie.view

import android.icu.text.DecimalFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object ReservationUiFormatter {
    fun localDateToUI(date: LocalDate): String = date.format(DATE_UI_YYYY_MM_DD_DOT)

    fun movieTimeToUI(time: Int): String = TIME_UI_COLON.format(time)

    fun priceToUI(price: Int): String = PRICE_UI_WITH_COMMA.format(price)

    private val DATE_UI_YYYY_MM_DD_DOT = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private const val TIME_UI_COLON = "%d:00"
    private val PRICE_UI_WITH_COMMA = DecimalFormat("#,###")
}
