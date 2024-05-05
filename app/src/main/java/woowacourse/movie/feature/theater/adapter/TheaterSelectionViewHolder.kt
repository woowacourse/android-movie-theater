package woowacourse.movie.feature.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.Theater

class TheaterSelectionViewHolder(
    private val binding: ItemTheaterBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.theaterViewHolder = this
    }

    fun bind(
        item: Theater,
        screeningCount: Int,
        onTheaterSelected: OnTheaterSelected,
    ) {
        with(binding) {
            name.text = item.name
            screeningInfo.text =
                itemView.context.getString(
                    R.string.theater_screening_time_count,
                    screeningCount,
                )
            constraintLayoutItemTheater.setOnClickListener {
                onTheaterSelected(item.theaterId)
            }
        }
    }
}
