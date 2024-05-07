package woowacourse.movie.domain.model

import java.io.Serializable

enum class SeatRank(val price: Int) : Serializable {
    S(15_000),
    A(12_000),
    B(10_000),
}
