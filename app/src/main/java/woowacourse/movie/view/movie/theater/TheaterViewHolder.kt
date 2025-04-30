package woowacourse.movie.view.movie.theater

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.view.movie.MovieClickListener

class TheaterViewHolder(
    view: View,
    private val clickListener: MovieClickListener,
) : RecyclerView.ViewHolder(view) {
    private val theaterNameTextView: TextView = view.findViewById(R.id.tv_theater_name)
    private val timeslotTextView: TextView = view.findViewById(R.id.tv_time_slot)

    fun bind(
        theater: Theater,
        movie: Movie,
    ) {
        theaterNameTextView.text = theater.name
        timeslotTextView.text = "${theater.getTotalTimeSlotCount(movie)}개의 상영 시간"
    }
}
