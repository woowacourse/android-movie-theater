package woowacourse.movie.domain.model

@JvmInline
value class HeadCount private constructor(val value: Int = INITIAL_VALUE) {
    fun increase(): HeadCount = HeadCount(value + 1)

    fun decrease(): HeadCount =
        if(value > INITIAL_VALUE) HeadCount(value - 1) else this

    companion object {
        private const val INITIAL_VALUE = 1

        fun of(value: Int = INITIAL_VALUE): HeadCount =
            HeadCount(value.coerceAtLeast(INITIAL_VALUE))
    }
}
