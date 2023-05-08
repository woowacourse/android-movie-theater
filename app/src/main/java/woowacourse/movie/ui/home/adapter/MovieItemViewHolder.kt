package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.uimodel.MovieListModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieItemViewHolder(
    private val binding: ItemMovieBinding,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onItemClick(adapterPosition) }
        binding.itemBookingButton.setOnClickListener { onItemClick(adapterPosition) }
    }

    fun bind(movie: MovieListModel.MovieModel) {
        binding.movie = movie
        binding.itemPoster.setImageResource(movie.poster)
        binding.itemDate.text = binding.root.context.getString(
            R.string.screening_date,
            movie.startDate.format(),
            movie.endDate.format(),
        )
    }

    private fun LocalDate.format(): String = format(
        DateTimeFormatter.ofPattern(binding.root.context.getString(R.string.date_format)),
    )
}
