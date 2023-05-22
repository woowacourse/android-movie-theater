package woowacourse.movie.data.model

import android.widget.TextView

class Counter(
    countView: TextView,
    savedStateKey: String
) : SaveStateTextView(countView, savedStateKey) {

    private var _count = MINIMUM_TICKET_COUNT
    val count
        get() = _count

    fun add(): Int {
        return ++_count
    }

    fun sub(): Int {
        _count = (--_count).coerceAtLeast(MINIMUM_TICKET_COUNT)
        return _count
    }

    companion object {
        private const val MINIMUM_TICKET_COUNT = 1
    }
}
