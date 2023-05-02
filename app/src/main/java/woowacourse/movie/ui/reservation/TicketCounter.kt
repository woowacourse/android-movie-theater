package woowacourse.movie.ui.reservation

import android.view.View
import android.widget.Button
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.model.CountState

class TicketCounter(
    view: View,
    presenter: MovieDetailContract.Presenter,
    initCountState: CountState? = null
) {
    private val minus: Button = view.findViewById(R.id.minus)
    private val plus: Button = view.findViewById(R.id.plus)
    private val countTextView: TextView = view.findViewById(R.id.count)

    init {
        initCountState?.let { countTextView.text = it.value.toString() }
        minus.setOnClickListener { presenter.minus() }
        plus.setOnClickListener { presenter.plus() }
    }

    fun setCounterText(count: Int) {
        countTextView.text = count.toString()
    }
}
