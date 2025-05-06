package woowacourse.movie.view

import android.icu.text.DecimalFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object ReservationUiFormatter {
    fun localDateToUI(date: LocalDate): String = date.format(DATE_UI_FORMATTER)

    fun movieTimeToUI(time: Int): String = TIME_UI_SUFFIX.format(time)

    fun movieTimeToLocalTime(time: Int): LocalTime = LocalTime.of(time, 0)

    fun priceToUI(price: Int): String = PRICE_UI_FORMATTER.format(price)

    private val DATE_UI_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private const val TIME_UI_SUFFIX = "%d:00"
    private val PRICE_UI_FORMATTER = DecimalFormat("#,###")
}
