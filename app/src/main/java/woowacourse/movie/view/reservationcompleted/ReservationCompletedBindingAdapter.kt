package woowacourse.movie.view.reservationcompleted

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.util.TIME_FORMATTER
import java.text.DecimalFormat
import java.time.LocalDateTime

@BindingAdapter("format", "screeningDateTime")
fun bindScreeningDateTime(
    textView: TextView,
    format: String,
    screeningDateTime: LocalDateTime
) {
    textView.text = format.format(
        screeningDateTime.toLocalDate().format(DATE_FORMATTER),
        screeningDateTime.toLocalTime().format(TIME_FORMATTER)
    )
}

@BindingAdapter("format", "reserveType", "peopleCount", "seats", "theaterName")
fun bindReservationDetail(
    textView: TextView,
    format: String,
    reserveType: String,
    peopleCount: Int,
    seats: List<String>,
    theaterName: String
) {
    textView.text = format.format(
        reserveType,
        peopleCount,
        seats.joinToString(),
        theaterName
    )
}

@BindingAdapter("stringFormat", "fee")
fun bindTotalPrice(
    textView: TextView,
    stringFormat: String,
    fee: Int
) {
    textView.text = stringFormat.format(
        DecimalFormat("#,###").format(fee)
    )
}
