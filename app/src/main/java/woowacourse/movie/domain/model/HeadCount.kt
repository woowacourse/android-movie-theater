package woowacourse.movie.domain.model

import java.io.Serializable

data class HeadCount(
    val value: Int = MINIMUM_HEAD_COUNT,
) : Serializable {
    init {
        require(value in MINIMUM_HEAD_COUNT..MAXIMUM_HEAD_COUNT) { ERROR_INVALID_HEAD_COUNT }
    }

    operator fun plus(count: Int): HeadCount = if (isMaximum()) this else HeadCount(value + count)

    operator fun minus(count: Int): HeadCount = if (isMinimum()) this else HeadCount(value - count)

    fun isMinimum(): Boolean = value == MINIMUM_HEAD_COUNT

    fun isMaximum(): Boolean = value == MAXIMUM_HEAD_COUNT

    companion object {
        private const val MINIMUM_HEAD_COUNT = 1
        private const val MAXIMUM_HEAD_COUNT = 20
        private const val ERROR_INVALID_HEAD_COUNT = "인원 수는 1~20명 사이여야 합니다."
    }
}
