package woowacourse.domain.ticket

enum class SeatRank(val price: Int) {
    S(15_000),
    A(12_000),
    B(10_000), ;

    companion object {
        fun find(ordinal: Int): SeatRank {
            return values().find { ordinal == it.ordinal } ?: throw IllegalArgumentException()
        }
    }
}
