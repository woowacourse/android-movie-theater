package woowacourse.movie.domain.model

class Price(val value: Int = DEFAULT_PRICE) {
    companion object {
        private const val DEFAULT_PRICE = 13000
    }
}
