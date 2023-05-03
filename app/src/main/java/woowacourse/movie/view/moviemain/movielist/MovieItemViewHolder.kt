package woowacourse.movie.view.moviemain.movielist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.view.model.MovieListModel.MovieUiModel

class MovieItemViewHolder(
    private val binding: MovieItemBinding,
    private val onViewClick: MovieListAdapter.OnItemClick
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieUiModel) {
        binding.apply {
            val context = binding.root.context
            moviePoster.setImageResource(movie.posterResourceId)
            movieTitle.text = movie.title
            movieScreeningDate.text =
                context.resources.getString(R.string.screening_date_format).format(
                    movie.screeningDates.min().format(DATE_FORMATTER),
                    movie.screeningDates.max().format(DATE_FORMATTER)
                )
            movieRunningTime.text = context.resources.getString(R.string.running_time_format)
                .format(movie.runningTime)
        }
        binding.reserveNowButton.setOnClickListener {
            onViewClick.onClick(movie)
        }
    }
}
