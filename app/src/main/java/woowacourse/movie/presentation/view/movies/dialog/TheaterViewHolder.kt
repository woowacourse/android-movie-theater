package woowacourse.movie.presentation.view.movies.dialog

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.model.TheaterUiModel

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onClickTheater: (TheaterUiModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var theater: TheaterUiModel? = null

    init {
        binding.ibNavigate.setOnClickListener {
            theater?.let {
                if (it.checkEnabledTheater()) onClickTheater(it)
            }
        }
    }

    fun bind(theater: TheaterUiModel) {
        this.theater = theater
        binding.tvTheaterName.text = theater.name
        binding.tvAvailableReservationCount.text =
            itemView.context.getString(
                R.string.item_theater_available_reservation_time_count,
                theater.times.count(),
            )

        val isEnabledTheater = theater.checkEnabledTheater()
        binding.ibNavigate.isEnabled = isEnabledTheater
        itemView.alpha = if (isEnabledTheater) ENABLED_ALPHA_VALUE else DISABLED_ALPHA_VALUE
    }

    private fun TheaterUiModel.checkEnabledTheater() = this.times.count() > MINIMUM_AVAILABLE_RESERVATION_TIME_COUNT

    companion object {
        private const val MINIMUM_AVAILABLE_RESERVATION_TIME_COUNT = 0
        private const val ENABLED_ALPHA_VALUE = 1f
        private const val DISABLED_ALPHA_VALUE = 0.4f
    }
}
