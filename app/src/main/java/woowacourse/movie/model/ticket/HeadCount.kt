package woowacourse.movie.model.ticket

import woowacourse.movie.model.result.ChangeTicketCountResult
import woowacourse.movie.model.result.Failure
import woowacourse.movie.model.result.Success
import java.io.Serializable

class HeadCount(
    count: Int = DEFAULT_HEAD_COUNT,
) : Serializable {
    var count: Int = count
        private set

    fun increase(): ChangeTicketCountResult {
        if (count >= MAX_HEAD_COUNT) return Failure
        count++
        return Success
    }

    fun decrease(): ChangeTicketCountResult {
        if (count <= MIN_HEAD_COUNT) return Failure
        count--
        return Success
    }

    companion object {
        const val DEFAULT_HEAD_COUNT = 1
        private const val MAX_HEAD_COUNT = 20
        private const val MIN_HEAD_COUNT = 1
    }
}
