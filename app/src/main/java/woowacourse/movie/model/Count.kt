package woowacourse.movie.model

class Count(number: Int = INITIAL_COUNT) {
    var value: Int = number
        private set

    init {
        require(number >= INITIAL_COUNT) { ERROR_NON_POSITIVE_NUMBER + "Input : $number" }
    }

    fun increase() = value++

    fun decrease() {
        if (value > 1) value--
    }

    fun update(newValue: Int) {
        value = newValue
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Count) return false
        return this.value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    companion object {
        private const val INITIAL_COUNT = 1
        private const val ERROR_NON_POSITIVE_NUMBER = "구매 티켓은 1개 이상이어야 합니다. "
    }
}
