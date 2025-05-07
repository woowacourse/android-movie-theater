package woowacourse.movie.model

@JvmInline
value class HeadCount(val value: Int) {
    fun plus(): HeadCount = HeadCount(value + 1)

    fun minus(): HeadCount {
        return if (value > MINIMUM_HEAD_COUNT_VALUE) HeadCount(value - 1) else this
    }

    companion object {
        private const val MINIMUM_HEAD_COUNT_VALUE = 1
    }
}
