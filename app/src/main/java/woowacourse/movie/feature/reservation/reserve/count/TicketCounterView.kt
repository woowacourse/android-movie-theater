package woowacourse.movie.feature.reservation.reserve.count

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.Toaster

class TicketCounterView(
    private val binding: ActivityMovieDetailBinding,
    initCountState: Int? = null
) : TicketCounterContract.View {

    val presenter: TicketCounterContract.Presenter = TicketCounterPresenter(this)
    val count: Int
        get() = presenter.countNumber

    init {
        initCountState?.let { presenter.setCountState(it) }
    }

    override fun setCountNumber(count: Int) {
        binding.ticketCount.text = count.toString()
    }

    override fun showLimitMin() {
        Toaster.showToast(binding.root.context, binding.root.context.getString(R.string.error_reservation_min_count))
    }

    override fun showLimitMax() {
        Toaster.showToast(binding.root.context, binding.root.context.getString(R.string.error_reservation_max_count))
    }
}
