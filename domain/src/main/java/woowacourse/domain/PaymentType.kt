package woowacourse.domain

enum class PaymentType {
    OFFLINE,
    ONLINE, ;

    companion object {
        fun find(ordinal: Int): PaymentType {
            return values().find { ordinal == it.ordinal } ?: throw IllegalArgumentException()
        }
    }
}
