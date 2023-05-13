package woowacourse.movie.view.activities.home.fragments.reservationlist

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import java.time.format.DateTimeFormatter

class ReservationItemViewHolder(
    private val view: View,
    private val onClick: (Long) -> Unit
) : RecyclerView.ViewHolder(view) {
    private val screeningDateTimeView = view.findViewById<TextView>(R.id.tv_screening_date_time)
    private val movieTitleView = view.findViewById<TextView>(R.id.tv_movie_title)

    fun bind(reservationListViewItemUIState: ReservationListViewItemUIState) {
        screeningDateTimeView.text =
            DATE_TIME_FORMATTER.format(reservationListViewItemUIState.screeningDateTime)
        movieTitleView.text = reservationListViewItemUIState.movieTitle
        view.setOnClickListener { onClick(reservationListViewItemUIState.reservationId) }
    }

    companion object {
        private val DATE_TIME_FORMATTER: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")
    }
}
