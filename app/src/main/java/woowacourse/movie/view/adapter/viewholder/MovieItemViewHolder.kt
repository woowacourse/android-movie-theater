package woowacourse.movie.view.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import domain.Movie
import woowacourse.movie.R
import woowacourse.movie.model.mapper.MovieMapper.toUi
import woowacourse.movie.model.MovieUiModel
import java.time.format.DateTimeFormatter

class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val posterImageView: ImageView
    private val titleTextView: TextView
    private val movieDateTextView: TextView
    private val runningTimeTextView: TextView
    private val reservationButton: Button

    init {
        posterImageView = view.findViewById(R.id.item_movie_poster)
        titleTextView = view.findViewById(R.id.item_movie_title)
        movieDateTextView = view.findViewById(R.id.item_movie_date)
        runningTimeTextView = view.findViewById(R.id.item_movie_running_time)
        reservationButton = view.findViewById(R.id.item_movie_reservation_button)
    }

    fun bind(movieUiModel: MovieUiModel, onClickEvent: (MovieUiModel) -> Unit) {
        posterImageView.setImageResource(movieUiModel.picture)
        val dateFormat =
            DateTimeFormatter.ofPattern(movieDateTextView.context.getString(R.string.movie_date_format))
        movieDateTextView.text = movieDateTextView.context.getString(R.string.movie_date).format(
            dateFormat.format(movieUiModel.startDate),
            dateFormat.format(movieUiModel.endDate)
        )
        runningTimeTextView.text =
            runningTimeTextView.context.getString(R.string.movie_running_time)
                .format(movieUiModel.runningTime)
        titleTextView.text = movieUiModel.title
        reservationButton.setOnClickListener { onClickEvent(movieUiModel) }
    }
}
