package woowacourse.movie.ui.reservation

import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.CountState

class TicketCounter(
    private val binding: ActivityMovieDetailBinding,
    presenter: MovieDetailContract.Presenter,
    initCountState: CountState? = null
) {
    init {
        initCountState?.let { binding.count.text = it.value.toString() }
        binding.minus.setOnClickListener { presenter.minus() }
        binding.plus.setOnClickListener { presenter.plus() }
    }

    fun setCounterText(count: Int) {
        binding.count.text = count.toString()
    }
}
