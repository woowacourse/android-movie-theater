package woowacourse.movie.view.movies.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.MovieScreeningInfoByTheater

class TheaterViewHolder(
    private val binding: ItemTheaterBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movieScreeningInfoByTheaters: MovieScreeningInfoByTheater) {
        binding.movieScreeningInfoByTheaters = movieScreeningInfoByTheaters
    }
}