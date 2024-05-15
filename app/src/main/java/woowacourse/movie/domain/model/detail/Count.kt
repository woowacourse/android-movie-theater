package woowacourse.movie.domain.model.detail

@JvmInline
value class Count(val number: Int) {
    init {
        require(number in RANGE) { "개수는 1이상 20이하 이어야 합니다." }
    }

    operator fun inc(): Count {
        return Count(number + 1)
    }

    operator fun dec(): Count {
        return Count(number - 1)
    }

    companion object {
        const val MIN_COUNT = 1
        const val MAX_COUNT = 20
        val RANGE = MIN_COUNT..MAX_COUNT
    }
}
