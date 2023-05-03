package woowacourse.movie.feature.detail.counter

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.common.Toaster
import woowacourse.movie.model.CountState

class ReservationCounter(
    val binding: ActivityMovieDetailBinding,
    initCountState: CountState? = null
) : CounterContract.View {
    private val presenter: CounterContract.Presenter = CounterPresenter(this)
    val count: CountState
        get() = presenter.countNumber

    init {
        initCountState?.let { presenter.setCountState(it) }
        initSetOnClickListener()
    }

    private fun initSetOnClickListener() {
        binding.minus.setOnClickListener { presenter.minus() }
        binding.plus.setOnClickListener { presenter.plus() }
    }

    override fun setCountNumber(count: CountState) {
        binding.count.text = count.value.toString()
    }

    override fun showLimitMin() {
        Toaster.showToast(
            binding.root.context,
            binding.root.context.getString(R.string.error_reservation_min_count)
        )
    }

    override fun showLimitMax() {
        Toaster.showToast(
            binding.root.context,
            binding.root.context.getString(R.string.error_reservation_min_count)
        )
    }
}
