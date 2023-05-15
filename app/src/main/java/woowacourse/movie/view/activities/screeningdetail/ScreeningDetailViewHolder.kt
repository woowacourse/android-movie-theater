package woowacourse.movie.view.activities.screeningdetail

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityScreeningDetailBinding
import java.time.format.DateTimeFormatter

class ScreeningDetailViewHolder(private val binding: ActivityScreeningDetailBinding) {

    fun bind(screeningDetailUIState: ScreeningDetailUIState) {
        binding.moviePosterIv.setImageResource(screeningDetailUIState.poster)
        binding.movieTitleTv.text = screeningDetailUIState.title
        binding.screeningRangeTv.text =
            binding.root.resources.getString(R.string.screening_range_format)
                .format(
                    DATE_FORMATTER.format(screeningDetailUIState.screeningStartDate),
                    DATE_FORMATTER.format(screeningDetailUIState.screeningEndDate)
                )
        binding.runningTimeTv.text = binding.root.resources.getString(R.string.running_time_format)
            .format(screeningDetailUIState.runningTime)
        binding.movieSummaryTv.text = screeningDetailUIState.summary
    }

    companion object {
        private val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    }
}