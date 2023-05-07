package woowacourse.movie.feature.reservation.reserve.count

import android.os.Bundle
import replaced.reservation.TicketCount
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.Toaster
import woowacourse.movie.model.replaced.TicketCountState

class TicketCounterView(
    private val binding: ActivityMovieDetailBinding
) : TicketCounterContract.View {

    val presenter: TicketCounterContract.Presenter by lazy { TicketCounterPresenter(this) }
    val count: Int
        get() = presenter.countNumber

    override fun setCountNumber(count: Int) { binding.ticketCount.text = count.toString() }

    fun set(instance: Bundle?) {
        setTicketCountTextView(instance)
        binding.minus.setOnClickListener { presenter.minus() }
        binding.plus.setOnClickListener { presenter.plus() }
    }

    private fun setTicketCountTextView(instance: Bundle?) {
        binding.ticketCount.text = TicketCount.MINIMUM.toString()
        if (instance != null) binding.ticketCount.text =
            instance.getInt(TicketCountState.TICKET_COUNT_INSTANCE_KEY).toString()
    }

    override fun showMinLimit() {
        Toaster.showToast(
            binding.root.context,
            binding.root.context.getString(R.string.error_reservation_min_count)
        )
    }

    override fun showMaxLimit() {
        Toaster.showToast(
            binding.root.context,
            binding.root.context.getString(R.string.error_reservation_max_count)
        )
    }
}
