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
        theaterNameTextView.text =
            itemView.context.getString(R.string.bottom_sheet_dialog_theater_name, theater.name)
        timeslotTextView.text =
            itemView.context.getString(
                R.string.bottom_sheet_dialog_time_slot,
                theater.getTotalTimeSlotCount(movie),
            )
    }
}
