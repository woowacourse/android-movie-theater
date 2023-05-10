package woowacourse.movie.view.moviereservation.widget

import android.widget.Button
import android.widget.TextView

class Counter(
    private val minusButton: Button,
    private val plusButton: Button,
    private val countText: TextView,
    savedStateKey: String
) : SaveStateTextView(countText, saveStateKey = savedStateKey) {
    init {
        applyToView(INITIAL_COUNT.toString())
    }

    fun setButtonsClick(minusButtonClick: () -> Unit, plusButtonClick: () -> Unit) {
        minusButton.setOnClickListener {
            minusButtonClick()
        }
        plusButton.setOnClickListener {
            plusButtonClick()
        }
    }

    fun getCount(): Int {
        return countText.text.toString().toInt()
    }

    companion object {
        private const val INITIAL_COUNT = 1
    }
}
