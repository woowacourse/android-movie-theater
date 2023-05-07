package woowacourse.movie.feature.reservation.reserve.selection

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.Toaster
import woowacourse.movie.model.CountState

class ReservationCounter(
    private val binding: ActivityMovieDetailBinding,
    initCountState: CountState? = null
) {
    var count: CountState = CountState.of(1)
        private set(value) {
            field = value
            binding.ticketCount.text = field.value.toString()
        }

    init {
        initCountState?.let { count = it }
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.minus.setOnClickListener {
            if (count.value == 1) {
                Toaster.showToast(it.context, it.context.getString(R.string.error_reservation_min_count))
                return@setOnClickListener
            }
            count -= 1
        }

        binding.plus.setOnClickListener { count += 1 }
    }
}
