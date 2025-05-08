package woowacourse.movie.presentation.bindingadapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.presentation.ReservationUiFormatter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter(value = ["startDate", "endDate"])
fun setScreeningDate(
    view: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val text =
        view.context.getString(
            R.string.movie_screening_date,
            ReservationUiFormatter.localDateToUI(startDate),
            ReservationUiFormatter.localDateToUI(endDate),
        )
    view.text = text
}

@BindingAdapter("reservationDateTime", "reservationTheaterName")
fun setReservationInfo(
    view: TextView,
    dateTime: LocalDateTime,
    theaterName: String,
) {
    val date = dateTime.format(DateTimeFormatter.ofPattern("yyyy.M.d"))
    val time = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    view.text = view.context.getString(R.string.reservation_list_info, date, time, theaterName)
}
