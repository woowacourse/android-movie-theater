package woowacourse.movie.domain.theater

import woowacourse.movie.domain.price.Price

enum class Grade(val price: Price) {
    B(Price(10000)), S(Price(15000)), A(Price(12000))
}
