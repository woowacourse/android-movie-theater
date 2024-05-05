package woowacourse.movie.feature.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.theater.Theater

class TheaterSelectionViewHolder(
    private val binding: ItemTheaterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.theaterViewHolder = this
    }

    fun bind(
        item: Theater,
        movieId: Int,
        onTheaterSelected: OnTheaterSelected,
    ) {
        with(binding) {
            name.text = item.name
            // TODO Presenter 활용하도록 변경
            val screeningTimeCount =
                TheaterDao().findScreeningTimesByMovieId(item.theaterId, movieId).size
            screeningInfo.text =
                itemView.context.getString(
                    R.string.theater_screening_time_count,
                    screeningTimeCount,
                )
            constraintLayoutItemTheater.setOnClickListener {
                onTheaterSelected(item.theaterId)
            }
        }
    }
}
