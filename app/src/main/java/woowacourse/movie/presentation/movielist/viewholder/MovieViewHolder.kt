package woowacourse.movie.presentation.movielist.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.presentation.movielist.MovieItem
import woowacourse.movie.presentation.util.formatDotDate

class MovieViewHolder(private val binding: MovieListItemBinding, clickBook: (Long) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.buttonItemBook.setOnClickListener { clickBook((adapterPosition + ADDITIONAL_POSITION).toLong()) }
    }

    fun bind(item: MovieItem.Movie) {
        val movie = item.movie
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

    companion object {
        private const val ADDITIONAL_POSITION = 1
    }
}
