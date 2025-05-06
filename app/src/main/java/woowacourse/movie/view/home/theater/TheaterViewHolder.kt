package woowacourse.movie.view.home.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.TheaterMovieSchedule

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onTheaterClick: (TheaterMovieSchedule) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(theaterMovieSchedule: TheaterMovieSchedule) {
        binding.movieScreeningInfoByTheater = theaterMovieSchedule

        binding.theaterButton.setOnClickListener {
            onTheaterClick.invoke(theaterMovieSchedule)
        }
    }
}
