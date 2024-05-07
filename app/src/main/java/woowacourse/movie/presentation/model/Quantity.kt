package woowacourse.movie.presentation.model

data class Quantity(val count: Int) {
    fun increase(quantity: Int): Quantity = copy(count = count + quantity)

    fun decrease(quantity: Int): Quantity = copy(count = count - quantity)

    fun isInvalidCount(): Boolean {
        return count < MIN_COUNT || count > MAX_COUNT
    }

    companion object {
        const val MAX_COUNT = 10
        const val MIN_COUNT = 1
    }
}
