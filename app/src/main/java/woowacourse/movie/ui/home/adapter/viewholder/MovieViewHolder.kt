package woowacourse.movie.ui.home.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.MovieListModel.MovieModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onClick: (MovieModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(movie: MovieModel) {
        with(binding) {
            root.setOnClickListener { onClick(movie) }
            itemPoster.setImageResource(movie.poster)
            itemTitle.text = movie.title
            itemDate.text = root.context.getString(
                R.string.screening_date,
                movie.startDate.format(),
                movie.endDate.format(),
            )
            itemBookingButton.text =
                root.context.getString(R.string.running_time, movie.runningTime)
        }
    }

    private fun LocalDate.format(): String = format(
        DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)),
    )
}
