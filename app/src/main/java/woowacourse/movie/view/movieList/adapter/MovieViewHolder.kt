package woowacourse.movie.view.movieList.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.MovieModel
import woowacourse.movie.utils.movieDetailFormat

class MovieViewHolder(view: View, private val onItemViewClick: (Int) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val moviePoster: ImageView by lazy { view.findViewById(R.id.item_poster) }
    private val movieTitle: TextView by lazy { view.findViewById(R.id.item_title) }
    private val movieDate: TextView by lazy { view.findViewById(R.id.item_date) }
    private val movieTime: TextView by lazy { view.findViewById(R.id.item_running_time) }
    private val bookingButton: Button by lazy { view.findViewById(R.id.item_booking_button) }

    init {
        view.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
        bookingButton.setOnClickListener { onItemViewClick(absoluteAdapterPosition) }
    }

    fun bind(movie: MovieModel) {
        moviePoster.setImageResource(movie.poster)
        movieTitle.text = movie.title
        movieDate.text = movie.getScreenDate()
        movieTime.text = movie.getRunningTime()
    }

    private fun MovieModel.getScreenDate(): String =
        movieDate.context.getString(
            R.string.screen_date,
            startDate.movieDetailFormat(),
            endDate.movieDetailFormat()
        )

    private fun MovieModel.getRunningTime(): String =
        movieTime.context.getString(R.string.running_time, runningTime)
}
