package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.uimodel.MovieListModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieItemViewHolder(
    parent: ViewGroup,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false),
) {
    private val binding = ItemMovieBinding.bind(itemView)

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
