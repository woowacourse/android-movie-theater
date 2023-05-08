package woowacourse.movie.presentation.movielist.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.movielist.movie.MovieItem
import woowacourse.movie.presentation.util.formatDotDate

class MovieViewHolder(
    private val binding: MovieListItemBinding,
    clickBook: (MovieModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var _movie: MovieModel? = null
    private val movie get() = _movie!!

    init {
        binding.buttonItemBook.setOnClickListener { clickBook(movie) }
    }

    fun bind(item: MovieItem.Movie) {
        _movie = item.movie
        binding.textItemTitle.text = movie.title
        binding.textBookingRunningTime.text =
            binding.textBookingRunningTime.context.getString(R.string.running_time)
                .format(movie.runningTime)
        binding.imageItemThumbnail.setImageResource(movie.thumbnail)
        binding.textBookingScreeningDate.apply {
            text = context.getString(R.string.screening_date)
                .format(
                    movie.screeningStartDate.formatDotDate(),
                    movie.screeningEndDate.formatDotDate(),
                )
        }
    }
}
