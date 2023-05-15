package woowacourse.movie.view.activities.home.fragments.screeninglist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemScreeningBinding
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.ScreeningUIState
import java.time.format.DateTimeFormatter

class ScreeningItemViewHolder(
    private val binding: ItemScreeningBinding,
    private val onReserveButtonClick: (Long) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(screeningUIState: ScreeningUIState) {
        binding.moviePosterIv.setImageResource(screeningUIState.poster)
        binding.movieTitleTv.text = screeningUIState.title
        binding.screeningRangeTv.text =
            binding.root.resources.getString(R.string.screening_range_format)
                .format(
                    DATE_FORMATTER.format(screeningUIState.screeningStartDate),
                    DATE_FORMATTER.format(screeningUIState.screeningEndDate)
                )
        binding.runningTimeTv.text = binding.root.resources.getString(R.string.running_time_format)
            .format(screeningUIState.runningTime)
        binding.reserveNowBtn.setOnClickListener { onReserveButtonClick(screeningUIState.screeningId) }
    }

    companion object {
        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}
