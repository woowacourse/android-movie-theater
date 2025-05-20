package woowacourse.movie.domain.reservation

enum class PurchaseType {
    DEFAULT,
    ;

    companion object {
        fun of(name: String): PurchaseType {
            return when (name) {
                "DEFAULT" -> DEFAULT
                else -> DEFAULT
            }
        }
    }
}
