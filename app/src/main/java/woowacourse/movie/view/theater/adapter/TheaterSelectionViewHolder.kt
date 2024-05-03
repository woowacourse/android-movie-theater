package woowacourse.movie.view.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.utils.MovieUtils.convertScreeningInfoFormat

class TheaterSelectionViewHolder(
    private val binding: ItemTheaterBinding,
    private val onSelectTheater: OnSelectTheater,
) : RecyclerView.ViewHolder(binding.root) {
    var screeningTimesFormatText = ""

    init {
        binding.theaterViewHolder = this
    }

    fun bind(item: Theater) {
        binding.theater = item

        val screeningTimeCount =
            item.screeningTimes.weekDay.size + item.screeningTimes.weekEnd.size
        screeningTimesFormatText = convertScreeningInfoFormat(
            screeningTimeCount,
            binding.root.context,
        )
    }

    fun onClickTheater(theaterId: Int) = onSelectTheater(theaterId)
}
