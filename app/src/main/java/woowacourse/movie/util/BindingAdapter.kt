package woowacourse.movie.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.ui.model.SeatUiModel
import java.time.LocalDate

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("startDate", "endDate")
    fun setScreeningPeriod(
        textView: TextView,
        screeningStartDate: LocalDate,
        screeningEndDate: LocalDate,
    ) {
        val formattedStartDate = Formatter.formatDateDotSeparated(screeningStartDate)
        val formattedEndDate = Formatter.formatDateDotSeparated(screeningEndDate)
        val formattedPeriod =
            textView.context.getString(
                R.string.text_screening_date,
                formattedStartDate,
                formattedEndDate,
            )

        textView.text = formattedPeriod
    }

    @JvmStatic
    @BindingAdapter("headCount", "seats", "theater")
    fun setTicketInfo(
        textView: TextView,
        headCount: Int,
        seats: Set<SeatUiModel>,
        theater: String,
    ) {
        val formattedSeats =
            seats.joinToString { point ->
                textView.context.getString(R.string.seat_point).format('A' + point.row, point.col + 1)
            }

        textView.text = textView.context.getString(R.string.formatted_screening_complete_ticket, headCount, formattedSeats, theater)
    }

    @JvmStatic
    @BindingAdapter("imageId")
    fun setMoviePoster(
        imageView: ImageView,
        resId: Int,
    ) {
        imageView.setImageResource(resId)
    }
}
