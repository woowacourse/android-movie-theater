package woowacourse.movie.view.widget

import woowacourse.movie.domain.model.Count

class Counter(
    var count: Count
) {
    fun add(): Int {
        count += COUNT_FACTOR
        return count.value
    }

    fun minus(): Int {
        count -= COUNT_FACTOR
        return count.value
    }

    companion object {
        const val COUNT_FACTOR = 1
    }
}
