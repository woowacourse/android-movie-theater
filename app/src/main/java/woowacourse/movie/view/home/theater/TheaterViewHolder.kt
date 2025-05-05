package woowacourse.movie.view.home.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.TheaterMovieSchedule

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onTheaterClick: (TheaterMovieSchedule) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onTheaterClick = onTheaterClick
    }

    fun bind(theaterMovieSchedule: TheaterMovieSchedule) {
        binding.movieScreeningInfoByTheater = theaterMovieSchedule
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onTheaterClick: (TheaterMovieSchedule) -> Unit,
        ): TheaterViewHolder =
            TheaterViewHolder(
                ItemTheaterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                ),
                onTheaterClick,
            )
    }
}
