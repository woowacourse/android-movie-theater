package woowacourse.movie.view.movies.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.MovieScreeningInfoByTheater

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onTheaterClick: (MovieScreeningInfoByTheater) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieScreeningInfoByTheater: MovieScreeningInfoByTheater) {
        binding.movieScreeningInfoByTheater = movieScreeningInfoByTheater

        binding.theaterButton.setOnClickListener {
            onTheaterClick.invoke(movieScreeningInfoByTheater)
        }
    }
}
