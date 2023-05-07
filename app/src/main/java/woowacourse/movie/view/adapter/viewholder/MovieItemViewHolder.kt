package woowacourse.movie.view.adapter.viewholder

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import domain.Movie
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.mapper.MovieMapper.toUi
import woowacourse.movie.model.MovieUiModel
import java.time.format.DateTimeFormatter

class MovieItemViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieUiModel: MovieUiModel, onClickEvent: (MovieUiModel) -> Unit) {
        binding.itemMoviePoster.setImageResource(movieUiModel.picture)
        val dateFormat =
            DateTimeFormatter.ofPattern(binding.itemMovieDate.context.getString(R.string.movie_date_format))
        binding.itemMovieDate.text =
            binding.itemMovieDate.context.getString(R.string.movie_date).format(
                dateFormat.format(movieUiModel.startDate),
                dateFormat.format(movieUiModel.endDate)
            )
        binding.itemMovieRunningTime.text =
            binding.itemMovieRunningTime.context.getString(R.string.movie_running_time)
                .format(movieUiModel.runningTime)
        binding.itemMovieTitle.text = movieUiModel.title
        binding.itemMovieReservationButton.setOnClickListener { onClickEvent(movieUiModel) }
    }
}
