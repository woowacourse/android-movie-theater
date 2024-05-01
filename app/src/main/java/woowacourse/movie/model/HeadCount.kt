package woowacourse.movie.model

import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success

class HeadCount(
    count: Int = DEFAULT_TICKET_COUNT,
) {
    var count: Int = count
        private set

    fun restore(count: Int): HeadCount {
        restoreCount(count)
        return this
    }

    fun increase(): ChangeTicketCountResult {
        if (count >= MAX_TICKET_COUNT) return Failure
        count++
        return Success
    }

    fun decrease(): ChangeTicketCountResult {
        if (count <= MIN_TICKET_COUNT) return Failure
        count--
        return Success
    }

    private fun restoreCount(recordOfCount: Int) {
        count = recordOfCount
    }

    companion object {
        private const val DEFAULT_TICKET_COUNT = 1
        private const val MAX_TICKET_COUNT = 20
        private const val MIN_TICKET_COUNT = 1
    }
}
