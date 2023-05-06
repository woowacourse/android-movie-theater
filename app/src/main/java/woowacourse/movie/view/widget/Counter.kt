package woowacourse.movie.view.widget

import woowacourse.movie.domain.model.Count

class Counter(
    private var _count: Count
) {
    val count get() = _count
    fun add(): Int {
        _count += COUNT_FACTOR
        return _count.value
    }

    fun minus(): Int {
        _count -= COUNT_FACTOR
        return _count.value
    }

    companion object {
        const val COUNT_FACTOR = 1
    }
}
