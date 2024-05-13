package woowacourse.movie.model

@JvmInline
value class HeadCount(val count: Int = MIN_COUNT) {
    init {
        require(count >= 0) { "머릿수는 0 이상이어야 합니다" }
    }

    fun increase(): HeadCount = HeadCount(count + INCREASE_COUNT)

    fun decrease(): HeadCount {
        if (count == MIN_COUNT) {
            return this
        }
        return HeadCount(count - INCREASE_COUNT)
    }

    companion object {
        const val MIN_COUNT = 1
        private const val INCREASE_COUNT = 1
    }
}
