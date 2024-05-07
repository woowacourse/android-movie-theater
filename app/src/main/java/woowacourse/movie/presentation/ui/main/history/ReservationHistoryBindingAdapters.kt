package woowacourse.movie.presentation.ui.main.history

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.R
import woowacourse.movie.presentation.utils.toReservationHistoryDate
import java.time.LocalDateTime

@BindingAdapter("localDateTime", "theaterName")
fun TextView.showReservationHistory(
    localDateTime: LocalDateTime,
    theaterName: String,
) {
    this.text =
        this.context.getString(
            R.string.reserve_history_description,
            localDateTime.toReservationHistoryDate(),
            theaterName,
        )
}
