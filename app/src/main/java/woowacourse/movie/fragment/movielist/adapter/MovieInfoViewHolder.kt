package woowacourse.movie.fragment.movielist.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.view.data.DateRangeViewData
import woowacourse.movie.view.data.MovieViewData
import java.time.format.DateTimeFormatter

class MovieInfoViewHolder(private val binding: ItemMovieBinding, onClickItem: (Int) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.itemMovieReservationButton.setOnClickListener {
            onClickItem(adapterPosition)
        }
    }

    fun bind(movieViewData: MovieViewData) {
        binding.apply {
            itemMoviePoster.setImageResource(movieViewData.poster.resource)
            itemMovieTitle.text = movieViewData.title
            itemMovieDate.text = formatDate(movieViewData.date)
            itemMovieRunningTime.text =
                root.context.getString(R.string.movie_running_time, movieViewData.runningTime)
        }
    }

    private fun formatDate(dateRangeViewData: DateRangeViewData): String {
        binding.apply {
            val dateFormat =
                DateTimeFormatter.ofPattern(root.context.getString(R.string.movie_date_format))
            return root.context.getString(
                R.string.movie_date,
                dateFormat.format(dateRangeViewData.startDate),
                dateFormat.format(dateRangeViewData.endDate)
            )
        }
    }
}
