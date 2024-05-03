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
        theaterId: OnSelectTheater,
    ) {
        with(binding) {
            name.text = item.name
            val screeningTimeCount =
                item.screeningTimes.weekDay.size + item.screeningTimes.weekEnd.size
            screeningInfo.text = itemView.context.getString(R.string.theater_screening_time_count, screeningTimeCount)
            constraintLayoutItemTheater.setOnClickListener {
                theaterId(item.theaterId)
            }
        }
    }
}
