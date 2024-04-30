package woowacourse.movie.presentation.movieList.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Theater

class TheaterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.item_theater_name_tv)
    private val time: TextView = view.findViewById(R.id.item_theater_time_tv)

    fun bind(
        theater: Theater,
        movieId: Long,
    ) {
        name.text = theater.name
        time.text =
            theater.screens
                .filter { screen -> screen.movieId == movieId }
                .flatMap { screen -> screen.screenSchedule }
                .size.toString()
    }
}
