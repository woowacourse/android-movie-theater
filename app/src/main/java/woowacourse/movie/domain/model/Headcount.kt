package woowacourse.movie.domain.model

import java.io.Serializable

class Headcount(
    count: Int = 1,
) : Serializable {
    var count: Int = count
        private set

    fun increase() {
        if (count < MAX_HEADCOUNT) {
            count++
        }
    }

    fun decrease() {
        if (count > MIN_HEADCOUNT) {
            count--
        }
    }

    companion object {
        private const val MIN_HEADCOUNT = 1
        private const val MAX_HEADCOUNT = 100
    }
}
