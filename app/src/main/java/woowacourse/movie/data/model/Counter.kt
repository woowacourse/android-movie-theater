package woowacourse.movie.data.model

import android.widget.Button
import android.widget.TextView

class Counter(
    minusButton: Button,
    plusButton: Button,
    private val countText: TextView,
    savedStateKey: String
) : woowacourse.movie.data.model.SaveStateTextView(countText, saveStateKey = savedStateKey) {
    init {
        applyToView(woowacourse.movie.data.model.Counter.Companion.INITIAL_COUNT.toString())
        minusButton.setOnClickListener {
            applyToView((getCount() - woowacourse.movie.data.model.Counter.Companion.COUNT_FACTOR).toString())
        }

        plusButton.setOnClickListener {
            applyToView((getCount() + woowacourse.movie.data.model.Counter.Companion.COUNT_FACTOR).toString())
        }
    }

    fun getCount(): Int {
        return countText.text.toString().toInt()
    }

    companion object {
        private const val INITIAL_COUNT = 1
        private const val COUNT_FACTOR = 1
    }
}
