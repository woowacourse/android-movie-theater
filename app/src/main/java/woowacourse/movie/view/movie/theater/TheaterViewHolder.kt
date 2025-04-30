package woowacourse.movie.view.movie.theater

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Theater
import woowacourse.movie.view.movie.MovieClickListener

class TheaterViewHolder(
    view: View,
    private val clickListener: MovieClickListener,
) : RecyclerView.ViewHolder(view) {
    private val theaterNameTextView: TextView = view.findViewById(R.id.tv_theater_name)


    fun bind(theater: Theater) {
        theaterNameTextView.text = theater.name
//        posterImageView.setImageResource(movie.poster)
//        screeningDateTextView.text =
//            itemView.context.getString(
//                R.string.movie_screening_date,
//                ReservationUiFormatter.localDateToUI(movie.startDate),
//                ReservationUiFormatter.localDateToUI(movie.endDate),
//            )
//        runningTimeTextView.text =
//            itemView.context.getString(R.string.movie_running_time, movie.runningTime)
//        reservationButton.setOnClickListener { clickListener.onReservationClick(movie) }
    }
}
